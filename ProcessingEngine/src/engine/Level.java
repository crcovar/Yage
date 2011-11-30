package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.BubbleDispenser;
import engine.tileobject.DeathZone;
import engine.tileobject.Platform;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.tileobject.VictoryZone;

/**
 * @author Charles Covar (covar1@gmail.com)
 */
public class Level extends GameObject {
	/**
	 * Reads in a level from a file and builds it out.
	 * @param player
	 * @param file
	 */
	public Level(LinkedList<Player> players, String file) {
		super();
		
		this.players = players;
		this.tiles = new LinkedList<TileObject>();
		
		this.eventManager = EventManager.getInstance();
		
		this.eventManager.registerListener(this, "move");
		this.eventManager.registerListener(this,"buildlevel");
		this.eventManager.registerListener(this, "syncplayer");
		
		this.script = "";
		
		try {
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			String line  = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    while( ( line = in.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    this.script = stringBuilder.toString();
		    this.eventManager.sendEvent("buildlevel", new EventData(script));
		    //this.buildLevel(stringBuilder.toString());
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void buildLevel(String level) {
		BufferedReader in = new BufferedReader(new StringReader(level));
		String line = null;
		
		try {
			while((line = in.readLine()) != null) {
				//check for comment
				if(line.startsWith("#")) {
					continue;
				}
				
				//do parsing				
				String[] lineArray = line.split(" ");
				
				if(lineArray.length > 0) {
					TileObject t = null;
					String obj = lineArray[0].toLowerCase();
					if(obj.equals("platform"))
						t = new Platform();
					else if(obj.equals("deathzone"))
						t = new DeathZone();
					else if(obj.equals("victoryzone"))
						t = new VictoryZone();
					else if(obj.equals("spawnpoint"))
						t = new SpawnPoint();
					else if(obj.equals("bubbledispenser"))
						t = new BubbleDispenser();
					else {
						continue;
					}
					
					if(lineArray.length > 1 && t != null) {
						for(int i=1;i<lineArray.length;i++) {
							String[] params = lineArray[i].split("=");
							t.setParam(params[0], params[1]);
						}
					}
					
					if(t instanceof SpawnPoint)
						this.spawn = (SpawnPoint) t;
					else
						this.tiles.add(t);
				}
			}
		} catch (IOException e) {
			this.eventManager.sendEvent("log", new EventData("Unable to read line"));
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Set the player to spawn at the levels spawn point, move the player there
	 * Make victory false, and record static elements of the level
	 */
	public void startLevel() {
		for(Player p : this.players) {
			p.setSpawn(spawn);
			p.moveToSpawn();
		}
	   this.victory = false;
	   
	   // record the platforms (since they only need to record once)
/*	   for(TileObject t : this.tiles) {
		   EventData e = new EventData();
		   if(t instanceof DeathZone) {
			   e.setMessage("DeathZone");
			   e.setObject((DeathZone) t);
		   } else if(t instanceof Platform) {
			   e.setMessage("Platform");
			   e.setObject((Platform) t);
		   } else if(t instanceof SpawnPoint) {
			   e.setMessage("SpawnPoint");
			   e.setObject((SpawnPoint) t);
		   } else if(t instanceof VictoryZone) {
			   e.setMessage("VictoryZone");
			   e.setObject((VictoryZone) t);
		   }
		   
		   //this.eventManager.sendEvent("record", e);
	   }
	   
	  // this.eventManager.sendEvent("record", new EventData("SpawnPoint",this.spawn)); 
*/
	}
	
	/**
	 * Add a player to the level.
	 * @param player <code>Player</code> object to add in
	 */
	public void addPlayer(Player player) {
		player.setSpawn(spawn);
		player.moveToSpawn();
		//return this.players.indexOf(player);
	}
	
	/**
	 * Add a <code>TileObject</code> into the level
	 * @param tile
	 */
	public void addTile(TileObject tile) {
		this.tiles.add(tile);
	}
	
	public String toString() {
		return this.script;
	}
	
	/**
	 * Tell the player to move
	 * @param direction Direction to move the player in
	 */
	public void movePlayer(String player, short direction) {
		for(Player p : this.players) {
			if(!p.getName().equals(player))
				continue;
			
			if(direction == Level.UP) p.moveUp();
			else if(direction == Level.LEFT) p.moveLeft();
			else if(direction == Level.RIGHT) p.moveRight();
			
			break;	// leave the loop because you moved the player you're looking for
		}
	}
	
	/**
	 * Return the spawnPoint for the level
	 * @return
	 */
	public SpawnPoint getSpawn() {
		return this.spawn;
	}
	
	/**
	 * Processes any events sent by the <code>EventManager</code>.
	 * @param name
	 * @param event
	 * @return
	 */
	@Override
	public boolean processMessage(String name, EventData event) {
		if(name.equals("move")) {
			this.movePlayer(event.getMessage(),(short) event.getValue());
			return true;
		} else if(name.equals("buildlevel")) {
			this.buildLevel(event.getMessage());
			this.startLevel();
		} else if(name.equals("syncplayer")) {
			for(Player p : this.players) {
				if(p.getName().equals(event.getMessage()))
					continue;
				p.collide(event.getX(), event.getY(), event.getWidth());	
			}
		}
		return false;
	}
	
	/**
	 * Updates the player and any objects in the level. Mostly collision detection.
	 */
	public void update() {
		for(Player p : this.players) {
			p.update();
			
		    this.eventManager.sendEvent("syncplayer", new EventData(p.getName(),p.getX(),p.getY(),p.getRadius(),p.getRadius()));
			
			for(TileObject t : tiles) {
				if(t.collide(p) && t instanceof VictoryZone) {
					this.victory = true;
				}
			}
		}
	}
	
	/**
	 * Checks to see if the player reached the end of the level
	 * @return true if the player reaches a <code>VictoryZone</code>
	 */
	public boolean reachedVictory() {
		return this.victory;
	}
	
	/**
	 * Draw every object in the level. First any <code>TileObject</code> then the player
	 */
	public void draw() {
		for(TileObject t : tiles) {
			t.draw();
		}
		for(Player p : this.players) {
			p.draw();
		}
	}
	
	public static final short UP = 0;
	public static final short DOWN = 1;
	public static final short LEFT = 2;
	public static final short RIGHT = 3;
	
	private SpawnPoint spawn;
	private LinkedList<Player> players;
	private LinkedList<TileObject> tiles;
	
	private EventManager eventManager;
	
	private String script;
	private boolean victory;

}

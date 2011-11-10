package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import engine.character.Player;
import engine.events.EventManager;
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
	public Level(Player player, String file) {
		super();
		
		this.players = new LinkedList<Player>();
		this.players.add(player);
		this.tiles = new LinkedList<TileObject>();
		
		this.eventManager = EventManager.getInstance();
		this.eventManager.registerListener(this,"player");
		
		try {
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			
			String line = in.readLine();
			while(line != null) {
				//check for comment
				if(line.startsWith("#")) {
					line = in.readLine();
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
					else {
						line = in.readLine();
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
								
				line = in.readLine();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
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
		this.players.add(player);
		player.setSpawn(spawn);
		player.moveToSpawn();
		//return this.players.indexOf(player);
	}
	
	/**
	 * Tell the player to move
	 * @param direction Direction to move the player in
	 */
	public void movePlayer(short direction) {
		if(direction == UP) players.get(0).moveUp();
		else if(direction == LEFT) players.get(0).moveLeft();
		else if(direction == RIGHT) players.get(0).moveRight();
	}
	
	/**
	 * Updates the player and any objects in the level. Mostly collision detection.
	 */
	public void update() {
		for(Player p : this.players) {
			p.update();
			
			for(int i=this.players.indexOf(p)+1;i<this.players.size();i++) {
				p.collide(this.players.get(i));
			}
			
			for(TileObject t : tiles) {
				if(t.collide(p) && t instanceof VictoryZone) {
					this.victory = true;
				}
			}
		
		}
		// record the players position
		//this.eventManager.sendEvent("record", new EventData("Player",this.player));
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
	
	private boolean victory;

}

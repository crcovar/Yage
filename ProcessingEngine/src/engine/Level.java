package engine;

import engine.character.Player;
import engine.events.EventMessage;
import engine.events.EventManager;
import engine.tileobject.DeathZone;
import engine.tileobject.Platform;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.tileobject.VictoryZone;

import java.util.LinkedList;
import java.io.*;

import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class Level extends GameObject {
	/**
	 * Reads in a level from a file and builds it out.
	 * @param player
	 * @param file
	 * @param parent
	 */
	public Level(Player player, String file, PApplet parent) {
		super();
		
		this.parent = parent;
		this.player = player;
		this.tiles = new LinkedList<TileObject>();
		
		this.eventManager = EventManager.getInstance();
		
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
	
	public void startLevel() {
	   player.setSpawn(spawn);
	   player.moveToSpawn();
	   this.victory = false;
	   
	   // record the platforms (since they only need to record once)
	   for(TileObject t : this.tiles) {
		   EventMessage e = new EventMessage();
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
		   
		   this.eventManager.sendEvent("record", e);
	   }
	}
	
	public void movePlayer(int direction) {
		if(direction == UP) player.moveUp();
		else if(direction == LEFT) player.moveLeft();
		else if(direction == RIGHT) player.moveRight();
	}
	
	public void update() {
		player.update();
		
		for(TileObject t : tiles) {
			if(t.collide(player) && t instanceof VictoryZone) {
				this.victory = true;
			}
		}
		
		// record the players position
		this.eventManager.sendEvent("record", new EventMessage("Player",this.player));
	}
	
	public boolean reachedVictory() {
		return this.victory;
	}
	
	public void draw() {
		for(TileObject t : tiles) {
			t.draw();
		}
		player.draw();
		
		this.parent.fill(255,255,255);
		this.parent.text("LIVE",10,20);
	}
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private SpawnPoint spawn;
	private Player player;
	private LinkedList<TileObject> tiles;
	
	private EventManager eventManager;
	
	private boolean victory;
	
	private PApplet parent;

}

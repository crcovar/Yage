package engine;

import engine.character.Player;
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
 *
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
						t = new Platform(this.parent);
					else if(obj.equals("deathzone"))
						t = new DeathZone(this.parent);
					else if(obj.equals("victoryzone"))
						t = new VictoryZone(this.parent);
					else if(obj.equals("spawnpoint"))
						t = new SpawnPoint(this.parent);
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
	}
	
	public boolean reachedVictory() {
		return this.victory;
	}
	
	public void draw() {
		for(TileObject t : tiles) {
			t.draw();
		}
		player.draw();
	}
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private SpawnPoint spawn;
	private Player player;
	private LinkedList<TileObject> tiles;
	
	private boolean victory;
	
	private PApplet parent;

}

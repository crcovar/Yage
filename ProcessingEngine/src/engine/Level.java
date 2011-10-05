package engine;

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
					if(lineArray[0].equals("Platform")) {
						Platform p;
						if(lineArray.length > 1) {
							p = new Platform(Integer.parseInt(lineArray[1]),
									Integer.parseInt(lineArray[2]),
									Integer.parseInt(lineArray[3]),
									Integer.parseInt(lineArray[4]), this.parent);
						} else {
							p = new Platform(this.parent);
						}
						
						this.tiles.add(p);
						
					}
					else if(lineArray[0].equals("SpawnPoint")) {
						SpawnPoint s = new SpawnPoint(Integer.parseInt(lineArray[1]),
														Integer.parseInt(lineArray[2]),
														this.parent);
						this.spawn = s; // leaving it like this to ease addition of multiple spawns
					}
					else if(lineArray[0].equals("DeathZone")) {
						DeathZone d;
						if(lineArray.length > 1) {
							d = new DeathZone(Integer.parseInt(lineArray[1]),
									Integer.parseInt(lineArray[2]),
									Integer.parseInt(lineArray[3]),
									Integer.parseInt(lineArray[4]),
									lineArray[5].equals("true"), this.parent);
						} else {
							d = new DeathZone();
						}
						this.tiles.add(d);
					}
					else if(lineArray[0].equals("VictoryZone")) {
						VictoryZone v = new VictoryZone(Integer.parseInt(lineArray[1]),
								Integer.parseInt(lineArray[2]), this.parent);
						this.tiles.add(v);
					}
				}
				
				line = in.readLine();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public Level(Player player, PApplet parent) {
		this.parent = parent;
		
	    this.spawn = new SpawnPoint(4,20, this.parent);
	    this.player = player;
	    Platform p1 = new Platform(this.parent);
	    Platform p2 = new Platform(18, 28, 3, 1, this.parent);
	    Platform p3 = new Platform(25,26,2, 1, this.parent);
	    Platform p4 = new Platform(20, 23, 4, 1, this.parent);
	    DeathZone d1 = new DeathZone();
	    DeathZone d2 = new DeathZone(22,27,6,2,true, this.parent);
	    VictoryZone v = new VictoryZone(20,18, this.parent);
	  
	    this.tiles = new LinkedList<TileObject>();
	    this.tiles.add(p1);
	    this.tiles.add(p2);
	    this.tiles.add(p3);
	    this.tiles.add(p4);
	    this.tiles.add(d1);
	    this.tiles.add(d2);
	    this.tiles.add(v);
	}
	
	public void startLevel() {
	   player.setSpawn(spawn);
	   player.moveToSpawn();
	}
	
	public void movePlayer(int direction) {
		if(direction == UP) player.moveUp();
		else if(direction == LEFT) player.moveLeft();
		else if(direction == RIGHT) player.moveRight();
	}
	
	public void update() {
		player.update();
		
		for(TileObject t : tiles) {
			t.collide(player);
		}
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
	
	private PApplet parent;

}

package engine.level;

import engine.Player;
import java.util.LinkedList;

import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Level {
	public Level(Player player, PApplet parent) {
		this.parent = parent;
		
	    this.spawn = new SpawnPoint(4,20, this.parent);
	    this.player = player;
	    Platform p1 = new Platform(this.parent);
	    Platform p2 = new Platform(18, 28, 3, this.parent);
	    Platform p3 = new Platform(25,26,2, this.parent);
	    Platform p4 = new Platform(20, 23, 4, this.parent);
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

package engine.level;

import engine.Player;

import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Level {
	public Level(Player player, PApplet parent) {
		this.player = player;
		this.parent = parent;
	}
	
	public void startLevel() {
	   // player.setSpawn(spawn);
	   // player.moveToSpawn();
	}
	
	public void movePlayer(int direction) {
		
	}
	
	public void update() {
		
	}
	
	public void draw() {
		player.draw();
	}
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private Player player;
	
	private PApplet parent;

}

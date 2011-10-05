/**
 * 
 */
package engine.tileobject;

import engine.GameObject;
import engine.Player;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class SpawnPoint extends GameObject { 
	public SpawnPoint(int x, int y, PApplet p) {
		this.x = x;
		this.y = y;
		
		this.parent = p;
	}
	  
	public int getX() { return x * TILE_SIZE; }
	public int getY() { return y * TILE_SIZE; } 
	  
	public void draw() {
		this.parent.fill(0,0,0);
		this.parent.stroke(255,255,255);
		this.parent.rect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
	}
	
	private int x, y;
	private final int TILE_SIZE = 16;
	
	private PApplet parent;
}
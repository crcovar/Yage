/**
 * 
 */
package engine;

import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class SpawnPoint extends GameObject { 
	public SpawnPoint(PApplet p) {
		super();
		
		this.x = 0;
		this.y = 0;
		
		this.parent = p;
	}
	
	public boolean setParam(String name, int value) {
		String n = name.toLowerCase();
		if(n.equals("x")) {
			this.x = value;
			return true;
		} else if(n.equals("y")) {
			this.y = value;
			return true;
		}
		
		return false;
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
package engine.tileobject;

import engine.GameObject;
import engine.character.Player;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class SpawnPoint extends GameObject implements TileObject { 
	public SpawnPoint(PApplet p) {
		super();
		
		this.x = 0;
		this.y = 0;
		
		this.parent = p;
	}
	
	public boolean setParam(String name, String value) {
		String n = name.toLowerCase();
		int v;
		
		try {
			v = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(n.equals("x")) {
			this.x = v;
			return true;
		} else if(n.equals("y")) {
			this.y = v;
			return true;
		}
		
		return false;
	}
	
	public String printParams() {
		return super.printParams() + " x="+this.x+" y="+this.y;
	}
	  
	public int getX() { return x * TILE_SIZE; }
	public int getY() { return y * TILE_SIZE; } 
	
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void draw() {
		this.parent.fill(0,0,0);
		this.parent.stroke(255,255,255);
		this.parent.rect(x*TILE_SIZE,y*TILE_SIZE,TILE_SIZE,TILE_SIZE);
	}
	
	private int x, y;
	
	private final int TILE_SIZE = 16;
	
	private PApplet parent;

}
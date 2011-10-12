package engine.tileobject;

import engine.GameObject;
import engine.character.Player;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class VictoryZone extends GameObject implements TileObject {

	public VictoryZone(PApplet p) {
		super();
		
		this.x = 0;
		this.y = 0;
		this.t_width=2;
		this.t_height=2;
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
	  
	public boolean collide(Player p) {
		int leftBound = p.getSmallLeftBound();
		int rightBound = p.getSmallRightBound();
		int topBound = p.getSmallTopBound();
		int bottomBound = p.getSmallBottomBound();
	    
		boolean collided = false;    
	    
		if((leftBound <= getRightBound()) && (rightBound >= getLeftBound())) { // we're in the x
			if((bottomBound >= getTopBound()) && topBound < getTopBound()) {
				collided = true;
				p.victory();
			}
			else if((topBound <= getBottomBound()) && bottomBound > getBottomBound()) {
				collided = true;
				p.victory();
			}
		}
	    
		if((topBound <= getBottomBound()) && (bottomBound >= getTopBound())) { // we're in the y
			if((rightBound >= getLeftBound()) && leftBound < getLeftBound()) {
				collided = true;
				p.victory();
			}
			else if((leftBound <= getRightBound()) && rightBound > getRightBound()) {
				collided = true;
				p.victory();
			}
		}   
	    
		return collided;
	}
	  
	public void draw() { 
		parent.fill(0,0,0);
		parent.stroke(255,255,0);
		for(int i=0; i<t_width; i++){
			for(int j=0;j<t_height;j++) {
				parent.rect(x*TILE_SIZE + (i*TILE_SIZE),y*TILE_SIZE + (j*TILE_SIZE),TILE_SIZE,TILE_SIZE);
			}
		}
	
	}
	
	private int getTopBound() { return y*TILE_SIZE; }
	private int getLeftBound() { return x*TILE_SIZE; }
	private int getRightBound() { return (x*TILE_SIZE + t_width*TILE_SIZE); }
	private int getBottomBound() { return (y*TILE_SIZE + t_height*TILE_SIZE); }
	
	private int x;
	private int y;
	  
	private int t_width;
	private int t_height;
	
	private PApplet parent;
}
package engine.tileobject;

import engine.GameObject;
import engine.character.Player;
import engine.events.EventManager;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class VictoryZone extends GameObject implements TileObject {

	public VictoryZone() {
		super();
		
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.t_width=2;
		this.t_height=2;
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
		} else if(n.equals("z")) {
			this.z = v;
			return true;
		}
		
		return false;
	}
	
	public String printParams() {
		return super.printParams() + " x="+this.x+" y="+this.y+" width="+this.t_width+" height="+this.t_height;
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
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getWidth() { return this.t_width; }
	public int getHeight() { return this.t_height; }
	  
	public void draw() { 
		EventManager.getInstance().sendEvent("draw", new EventData("VictoryZone", this.x, this.y, this.t_width, this.t_height));
	}
	
	private int getTopBound() { return y*TILE_SIZE; }
	private int getLeftBound() { return x*TILE_SIZE; }
	private int getRightBound() { return (x*TILE_SIZE + t_width*TILE_SIZE); }
	private int getBottomBound() { return (y*TILE_SIZE + t_height*TILE_SIZE); }
	
	private int x;
	private int y;
	private int z;
	  
	private int t_width;
	private int t_height;
}
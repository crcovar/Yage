package engine.tileobject;

import engine.GameObject;
import engine.character.Player;
import engine.events.EventManager;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class Platform extends GameObject implements TileObject {
	  
	public Platform() {
		super();
		
		this.x=0;
		this.y=29;
		this.t_width = 40;
		this.t_height = 1;
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
		} else if(n.equals("width")) {
			this.t_width = v;
			return true;
		} else if(n.equals("height")) {
			this.t_height = v;
			return true;
		}
		
		return false;
	}
	
	public String printParams() {
		return super.printParams() + " x="+this.x+" y="+this.y+" width="+this.t_width+" height="+this.t_height;
	}
	  
	public int getTopBound() { return y*TILE_SIZE; }
	public int getLeftBound() { return x*TILE_SIZE; }
	public int getRightBound() { return (x*TILE_SIZE + t_width*TILE_SIZE); }
	public int getBottomBound() { return (y*TILE_SIZE + t_height*TILE_SIZE); }
	  
	public boolean collide(Player p) {
		int leftBound = p.getLeftBound();
		int rightBound = p.getRightBound();
		int topBound = p.getTopBound();
		int bottomBound = p.getBottomBound();
	    
		boolean collided = false;   
	   
		if((p.getSmallLeftBound() <= this.getRightBound()) && (p.getSmallRightBound() >= this.getLeftBound())) { // we're in the x
			if((bottomBound >= this.getTopBound()) && topBound < this.getTopBound()) {
				collided = true;
				p.collideBottom(this.getTopBound());
				EventManager.getInstance().sendEvent("collidebottom", new EventData(p.getName(),this.getTopBound()));
			}
			else if((topBound <= this.getBottomBound()) && bottomBound > this.getBottomBound()) {
				collided = true;
				p.collideTop(this.getBottomBound());
				EventManager.getInstance().sendEvent("collidetop", new EventData(p.getName(),this.getBottomBound()));
			}
		}
	    
		if((p.getSmallTopBound() <= this.getBottomBound()) && (p.getSmallBottomBound() >= this.getTopBound())) { // we're in the y
			if((rightBound >= this.getLeftBound()) && leftBound < this.getLeftBound()) {
				collided = true;
				p.collideRight(this.getLeftBound());
				EventManager.getInstance().sendEvent("collideright", new EventData(p.getName(),this.getLeftBound()));
			}
			else if((leftBound <= this.getRightBound()) && rightBound > this.getRightBound()) {
				collided = true;
				p.collideLeft(this.getRightBound());
				EventManager.getInstance().sendEvent("collideleft", new EventData(p.getName(),this.getRightBound()));
			}
		}   
	    
		return collided;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getWidth() { return this.t_width; }
	public int getHeight() { return this.t_height; }
	  
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("Platform", this.x, this.y, this.t_width, this.t_height));	
	}
	
	private int x;
	private int y;
	private int t_width;
	private int t_height;
}
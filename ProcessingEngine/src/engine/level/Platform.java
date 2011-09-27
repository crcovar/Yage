package engine.level;

import processing.core.PApplet;
import engine.Player;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Platform implements TileObject {

	public Platform(int x, int y, int w, int h, PApplet p) {
		this.x = x;
		this.y = y;
		this.t_width = w;
		this.t_height = h;
		this.parent = p;
	}  
	  
	public Platform(PApplet p) {
		this.x=0;
		this.y=29;
		this.t_width = 40;
		this.t_height = 1;
		this.parent = p;
	}
	  
	public int getTopBound() { return y*TILE_SIZE; }
	public int getLeftBound() { return x*TILE_SIZE; }
	public int getRightBound() { return (x*TILE_SIZE + t_width*TILE_SIZE); }
	public int getBottomBound() { return (y*TILE_SIZE + TILE_SIZE); }
	  
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
			}
			else if((topBound <= this.getBottomBound()) && bottomBound > this.getBottomBound()) {
				collided = true;
				p.collideTop(this.getBottomBound());
			}
		}
	    
		if((p.getSmallTopBound() <= this.getBottomBound()) && (p.getSmallBottomBound() >= this.getTopBound())) { // we're in the y
			if((rightBound >= this.getLeftBound()) && leftBound < this.getLeftBound()) {
				collided = true;
				p.collideRight(this.getLeftBound());
			}
			else if((leftBound <= this.getRightBound()) && rightBound > this.getRightBound()) {
				collided = true;
				p.collideLeft(this.getRightBound());
			}
		}   
	    
		return collided;
	}
	  
	public void draw() {
		parent.fill(0,0,0);
		parent.stroke(255,0,0);
		for(int i=0; i<t_width; i++){
			for(int j=0;j<t_height;j++) {
				parent.rect(x*TILE_SIZE + (i*TILE_SIZE),y*TILE_SIZE + (j*TILE_SIZE),TILE_SIZE,TILE_SIZE);
			}
		}
	
	}
	
	private int x;
	private int y;
	private int t_width;
	private int t_height;
	
	private PApplet parent;
}
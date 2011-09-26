/**
 * 
 */
package engine.level;

import engine.Player;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class DeathZone implements TileObject {
	  
	public DeathZone(int x, int y, int w, int h, boolean draw, PApplet p) {
		this.x = x;
		this.y = y;
		this.t_width=w;
		this.t_height=h;
		this.draw = draw;
		this.parent = p;
	}
	  
	public DeathZone() {
		this.x = -3;
		this.y = 32;
		this.t_width = 45;
		this.t_height = 8;
		this.draw = false;
		this.parent = null;
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
				p.death();
			}
			else if((topBound <= getBottomBound()) && bottomBound > getBottomBound()) {
				collided = true;
				p.death();
			}
		}
		
		if((topBound <= getBottomBound()) && (bottomBound >= getTopBound())) { // we're in the y
			if((rightBound >= getLeftBound()) && leftBound < getLeftBound()) {
				collided = true;
				p.death();
			}
			else if((leftBound <= getRightBound()) && rightBound > getRightBound()) {
				collided = true;
				p.death();
			}
		}   
		
		return collided;
	}
	  
	public void draw() { 
		if(draw) {
			parent.fill(0,0,0);
			parent.stroke(0,0,255);
			for(int i=0; i<t_width; i++){
				for(int j=0;j<t_height;j++) {
					parent.rect(x*TILE_SIZE + (i*TILE_SIZE),y*TILE_SIZE + (j*TILE_SIZE),TILE_SIZE,TILE_SIZE);
				}
			}
		} // end if
	
	}
	
	private int getTopBound() { return y*TILE_SIZE; }
	private int getLeftBound() { return x*TILE_SIZE; }
	private int getRightBound() { return (x*TILE_SIZE + t_width*TILE_SIZE); }
	private int getBottomBound() { return (y*TILE_SIZE + t_height*TILE_SIZE); }
	
	private int x;
	private int y;
	 
	private int t_width;
	private int t_height;
	  
	private boolean draw;
	private PApplet parent;
}
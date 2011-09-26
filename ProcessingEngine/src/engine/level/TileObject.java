/**
 * 
 */
package engine.level;

import engine.Player;

/**
 * @author Covar
 *
 */
public interface TileObject {
	final int TILE_SIZE = 16;
	
	public boolean collide(Player p);
	  
	public void draw();
}

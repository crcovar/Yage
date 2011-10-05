/**
 * 
 */
package engine.tileobject;

import engine.character.Player;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public interface TileObject {
	final int TILE_SIZE = 16;
	
	public boolean collide(Player p);
	  
	public void draw();
}

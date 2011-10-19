/**
 * 
 */
package engine.tileobject;

import engine.character.Player;

/**
 * @author Charles Covar (covar1@gmail.com)
 */
public interface TileObject {
	public static final short TILE_SIZE = 16;
	
	/**
	 * Check to see if a Character collides with a TileObject
	 * @param p character to check for collision
	 * @return true if a collision occurs
	 */
	public boolean collide(Player p);
	
	/**
	 * Renders the TileOjbect
	 */
	public void draw();

	/**
	 * Sets the value of an instance variable
	 * @param name name of the instance variable to set
	 * @param value
	 * @return true if the parameter exists and value was set correctly
	 */
	public boolean setParam(String name, String value);
	
	/**
	 * Prints the parameters and their values
	 * @return String of the objects parameters
	 */
	public String printParams();
	
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
}

package engine.tileobject;

import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;
import engine.utils.BubbleColors;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Bubble implements TileObject {
	
	/**
	 * Default Constructor
	 */
	public Bubble() {
	}

	/**
	 * Check collision
	 */
	@Override
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("Bubble",x,y));
	}

	@Override
	public boolean setParam(String name, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String printParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getWidth() {
		return TileObject.TILE_SIZE;
	}

	@Override
	public int getHeight() {
		return TileObject.TILE_SIZE;
	}
	
	private int x, y;
	private BubbleColors color;

}

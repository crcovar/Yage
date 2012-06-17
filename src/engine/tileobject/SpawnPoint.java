package engine.tileobject;

import engine.GameObject;
import engine.character.Player;
import engine.events.EventManager;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class SpawnPoint extends GameObject implements TileObject { 
	public SpawnPoint() {
		super();
		
		this.x = 0;
		this.y = 0;
		this.z = 0;
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
		return super.printParams() + " x="+this.x+" y="+this.y + " width=1 height=1";
	}
	  
	public int getX() { return x * TILE_SIZE; }
	public int getY() { return y * TILE_SIZE; }
	public int getWidth() { return 1; }
	public int getHeight() { return 1; }
	
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
		
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("SpawnPoint", this.x, this.y, 1, 1));
	}
	
	private int x, y, z;
	
	private final int TILE_SIZE = 16;

}
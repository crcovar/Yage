/**
 * 
 */
package engine.tileobject;

import engine.character.Bubble;
import engine.character.Player;

/**
 * @author Covar
 *
 */
public class BubbleDispenser implements TileObject {

	public BubbleDispenser() {
		this.x = 0;
		this.y = 0;
		this.t_height = 1;
		this.t_width = 40;
	}
	
	
	@Override
	public boolean collide(Player p) {
		return false;
	}

	@Override
	public void draw() {
	}

	@Override
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
			return false;
		} else if(n.equals("width")) {
			this.t_width = v;
			return true;
		} else if(n.equals("height")) {
			this.t_height = v;
			return true;
		}
		
		return false;
	}

	@Override
	public String printParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int x;
	private int y;
	private int t_width;
	private int t_height;
}

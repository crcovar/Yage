package engine.events;

import java.io.Serializable;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Write javadoc
 */
public class RenderEvent extends EventData implements Serializable {
	
	public RenderEvent() {
		super();
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	public RenderEvent(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}
	
	public RenderEvent(String msg, int x, int y) {
		this(x,y);
		this.setMessage(msg);
	}
	
	public RenderEvent(int x, int y, int width, int height) {
		this(x,y);
		this.width = width;
		this.height = height;
	}
	public RenderEvent(String msg, int x, int y, int width, int height) {
		this(x,y,width,height);
		this.setMessage(msg);
	}
	
	public boolean setParam(String name, String value) {
		
		int v = 0;
		try {
			v =Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(name.equals("x")) {
			this.x = v;
			return true;
		} else if(name.equals("y")) {
			this.y = v;
			return true;
		} else if(name.equals("width") || name.equals("w")) {
			this.width = v;
			return true;
		} else if(name.equals("height") || name.equals("h")) {
			this.height = v;
			return true;
		} else if(name.equals("radius") || name.equals("r")) {
			this.width = v*2;
			this.height = v*2;
			return true;
		} else
			return false;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	
	private static final long serialVersionUID = -7234706606575239774L;
	private int x;
	private int y;
	private int width;
	private int height;
}

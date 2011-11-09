package engine.events;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class EventData {
	
	public EventData() {
		this.msg = "";
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.guid = 0;
	}
	
	public EventData(String message) {
		this();
		this.msg = message;
	}
	
	public EventData(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}
	
	public EventData(String msg, int x, int y) {
		this(x,y);
		this.setMessage(msg);
	}
	
	public EventData(int x, int y, int width, int height) {
		this(x,y);
		this.width = width;
		this.height = height;
	}
	public EventData(String msg, int x, int y, int width, int height) {
		this(x,y,width,height);
		this.setMessage(msg);
	}
	
	public EventData(String msg, int guid, int x, int y, int width, int height) {
		this(x,y,width,height);
		this.setMessage(msg);
		this.guid = guid;
	}
	
	public void setMessage(String message) {
		this.msg = message;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	public GameObject getObject() {
	//	return this.object;
		return null;
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
		} else if(name.equals("guid")) {
			this.guid = v;
			return true;
		} else
			return false;
	}

	public int getGuid() { return guid; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	private int guid;
	private int x;
	private int y;
	private int width;
	private int height;
		
	private String msg;

}

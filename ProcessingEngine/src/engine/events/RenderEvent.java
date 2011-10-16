/**
 * 
 */
package engine.events;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class RenderEvent extends EventMessage {
	
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
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	private int x;
	private int y;
	private int width;
	private int height;
}

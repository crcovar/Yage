/**
 * 
 */
package tools;

import processing.core.PApplet;
import engine.GameObject;
import engine.events.Event;
import engine.events.EventManager;
import engine.network.Connection;
import engine.utils.Logger;
import engine.utils.Renderer;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class RemoteVisualizer extends PApplet {

	public void setup() {
		size(640,480, P2D);  // screen size of 640x480 gives 40x30 tilemap
		frameRate(30);
		this.eventManager = EventManager.getInstance();
		
		new Logger("out2.log");
		new Renderer(this);
		connection = new Connection("localhost", 10040);
		this.connection.send(new Event("null",null));
	}

	public void draw() {
		GameObject.gameTime++;
		this.eventManager.sendEvent("clear", null);
		
		this.eventManager.process();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[] { "tools.RemoteVisualizer" });

	}
	
	private static final long serialVersionUID = 3848447693892875865L;

	private EventManager eventManager;
	private Connection connection;
}

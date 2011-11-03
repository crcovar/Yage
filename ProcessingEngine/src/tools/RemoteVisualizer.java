/**
 * 
 */
package tools;

import processing.core.PApplet;
import engine.network.Client;
import engine.utils.Logger;
import engine.utils.Renderer;
import engine.events.*;

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
		this.eventManager.registerListener(new Client("localhost", 10040), "register");

		this.eventManager.sendEvent("register", new EventData("clear"));
		this.eventManager.sendEvent("register", new EventData("draw"));
		this.eventManager.sendEvent("register", new EventData("text"));
		this.eventManager.sendEvent("register", new EventData("selectedtext"));
		//*/
	}

	public void draw() {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[] { "tools.RemoteVisualizer" });

	}
	
	private static final long serialVersionUID = 3848447693892875865L;

	@SuppressWarnings("unused")
	private EventManager eventManager;
}

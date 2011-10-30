/**
 * 
 */
package tools;

import processing.core.PApplet;
import engine.Renderer;
import engine.network.Client;
import engine.utils.Logger;
import engine.events.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class RemoteVisualizer extends PApplet {

	public void setup() {
		size(640,480, P2D);  // screen size of 640x480 gives 40x30 tilemap
		
		this.eventManager = EventManager.getInstance();
		
		new Logger("out2.log");
		new Renderer(this);
		this.eventManager.registerListener(new Client(), "register");

		this.eventManager.sendEvent("register", new EventMessage("clear"));
		this.eventManager.sendEvent("register", new EventMessage("draw"));
		this.eventManager.sendEvent("register", new EventMessage("text"));
		this.eventManager.sendEvent("register", new EventMessage("selectedtext"));
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

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
		noLoop();
		
		this.eventManager = EventManager.getInstance();
		
		new Logger("out2.log");
		
		this.renderer = new Renderer(this);
		this.client = new Client();
		
	}

	public void draw() {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[] { "tools.RemoteVisualizer" });

	}

	private EventManager eventManager;
	private Renderer renderer; 
	private Client client;
}

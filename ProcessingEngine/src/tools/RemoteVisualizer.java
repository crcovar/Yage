/**
 * 
 */
package tools;

import processing.core.PApplet;
import engine.Renderer;
import engine.network.Client;
import engine.events.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class RemoteVisualizer extends PApplet {

	public void setup() {
		size(640,480, P2D);  // screen size of 640x480 gives 40x30 tilemap
		frameRate(30);
		
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

	private Renderer renderer; 
	private Client client;
}

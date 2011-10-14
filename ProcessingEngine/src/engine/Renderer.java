package engine;

import engine.events.EventManager;
import engine.events.EventMessage;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Add support for drawing objects
 */
public class Renderer extends GameObject {
	/**
	 * Constructor
	 * @param p </code>PApplet</code> object from processing, handles the drawing
	 */
	public Renderer(PApplet p) {
		this.parent = p;
		
		EventManager.getInstance().registerListener("draw", this);
		EventManager.getInstance().registerListener("clear", this);
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventMessage event) {
		if(name.equals("clear")) {
			this.clear();
			return true;
		}
		return false;
	}
	
	/**
	 * Used for processing the clear event. Draws the screen black
	 */
	private void clear() {
		this.parent.background(0,0,0);
	}

	PApplet parent;
}

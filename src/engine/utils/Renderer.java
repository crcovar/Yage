package engine.utils;

import java.awt.Color;

import org.newdawn.slick.*;

import engine.GameObject;
import engine.events.Event;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.TileObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
 */
public class Renderer extends GameObject {
	/**
	 * Constructor
	 * @param p </code>PApplet</code> object from processing, handles the drawing
	 */
	public Renderer(Graphics g) {
        this.graphics = g;
		Image tiles = this.parent.loadImage("assets/tilemap.png");
		
		// register our events
		EventManager.getInstance().registerListener(this, "draw");
		EventManager.getInstance().registerListener(this, "clear");
		EventManager.getInstance().registerListener(this, "text");
		EventManager.getInstance().registerListener(this, "selectedtext");
	}
	
	public boolean processEvent(Event event) {
		if(event.isLocal() || (!event.isLocal() && !event.getName().equals("clear"))) {
			return super.processEvent(event);
		} else
		
		return false;
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventData event) {
		if(name.equals("clear")) {
			this.graphics.clear();
			return true;
		} else if(name.equals("text") && event != null) {
			this.text(event.getMessage(), event.getX(), event.getY());
			return true;
		} else if(name.equals("selectedtext") && event != null) {
			this.selectedText(event.getMessage(), event.getX(), event.getY());
			return true;
		}
		return false;
	}
	
	private void text(String text, int x, int y) {
		this.graphics.drawString(x, y, text, Color.White);
	}
	
	private void selectedText(String text, int x, int y) {
		this.graphics.drawString(x, y, text, Color.Yellow);
	}

    private Graphics graphics;
	private Image tiles;
}

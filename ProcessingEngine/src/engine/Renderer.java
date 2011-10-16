package engine;

import engine.events.*;
import engine.tileobject.*;
import engine.character.*;
import engine.character.Character;
import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: write javadoc
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
		EventManager.getInstance().registerListener("text", this);
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventMessage event) {
		RenderEvent re = null;
		
		if(event instanceof RenderEvent)
			re = (RenderEvent) event;
		
		if(name.equals("clear")) {
			this.clear();
			return true;
		} else if(name.equals("draw")) {
			if(re.getMessage().equals("player")) {
				this.drawEllipse(re.getX(), re.getY(), re.getWidth(), re.getHeight());
				return true;
			}
			else
				return this.draw(re.getMessage(), re.getX(), re.getY(), re.getWidth(), re.getHeight());
		} else if(name.equals("text") && re != null) {
			this.text(re.getMessage(), re.getX(), re.getY());
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
	
	private void drawEllipse(int x, int y, int w, int h) {
		parent.stroke(255,255,255);
		parent.fill(255,255,255);
		parent.ellipse(x,y,w,h);
	}
	
	private boolean draw(String tileObject, int x, int y, int w, int h) {
		String s = tileObject.toLowerCase();
		this.parent.fill(0,0,0);
		
		if(s.equals("deathzone")) {
			parent.stroke(0,0,255);
		} else if (s.equals("platform")) {
			parent.stroke(255,0,0);
		} else if (s.equals("spawnpoint")) {
			parent.stroke(255,255,255);
		} else if (s.equals("victoryzone")) {
			parent.stroke(255,255,0);
		} else
			return false;
		
		for(int i=0; i<w; i++){
			for(int j=0;j<h;j++) {
				parent.rect(x*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),y*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
			}
		}
		
		return true;
	}
	
	private void text(String text, int x, int y) {
		this.parent.fill(255,255,255);
		this.parent.text(text,x,y);
	}

	private PApplet parent;
}

package engine;

import engine.events.EventManager;
import engine.events.EventMessage;
import engine.tileobject.*;
import engine.character.*;
import engine.character.Character;
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
		} else if(name.equals("draw")) {
			if(event.getMessage().equals("character"))
				return this.draw((Character) event.getObject());
			else
				return this.draw((TileObject) event.getObject());
		}
		return false;
	}
	
	/**
	 * Used for processing the clear event. Draws the screen black
	 */
	private void clear() {
		this.parent.background(0,0,0);
	}
	
	/**
	 * Draws a <code>Character</code> to the screen
	 * @param c any class that implements <code>Character</code>
	 * @return true if character is drawn
	 */
	private boolean draw(Character c) {
		if(c instanceof Player) {
			parent.stroke(255,255,255);
			parent.fill(255,255,255);
			parent.ellipse(c.getX(),c.getY(),c.getRadius()*2,c.getRadius()*2);
			return true;
		}
		return false;
	}
	
	/**
	 * Draw a <code>TileObject</code> to the screen
	 * @param t any class that implements <code>TileOjbect</code>
	 * @return true if the object is draw to the screen
	 */
	private boolean draw(TileObject t) {
		parent.fill(0,0,0);
		
		if(t instanceof SpawnPoint) {
			this.parent.stroke(255,255,255);
			this.parent.rect(t.getX(),t.getY(),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
			return true;
		} else {
			if(t instanceof DeathZone) {
				parent.stroke(0,0,255);
			} else if (t instanceof Platform) {
				parent.stroke(255,0,0);
			} else if (t instanceof VictoryZone) {
				parent.stroke(255,255,0);
			} else {
				return false;
			}
			
			for(int i=0; i<t.getWidth(); i++){
				for(int j=0;j<t.getHeight();j++) {
					parent.rect(t.getX()*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),t.getY()*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
				}
			}
			return true;
		}
	}

	private PApplet parent;
}

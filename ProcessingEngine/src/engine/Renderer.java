package engine;

import engine.events.EventManager;
import engine.events.EventMessage;
import engine.tileobject.*;
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
	
	private boolean draw(TileObject t) {
		parent.fill(0,0,0);
		if(t instanceof DeathZone) {
			parent.stroke(0,0,255);
			for(int i=0; i<t.getWidth(); i++){
				for(int j=0;j<t.getHeight();j++) {
					parent.rect(t.getX()*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),t.getY()*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
				}
			}
			return true;
		} else if (t instanceof Platform) {
			parent.stroke(255,0,0);
			for(int i=0; i<t.getWidth(); i++){
				for(int j=0;j<t.getHeight();j++) {
					parent.rect(t.getX()*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),t.getY()*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
				}
			}
			return true;
		} else if (t instanceof VictoryZone) {
			parent.stroke(255,255,0);
			for(int i=0; i<t.getWidth(); i++){
				for(int j=0;j<t.getHeight();j++) {
					parent.rect(t.getX()*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),t.getY()*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
				}
			}
			return true;
		} else if(t instanceof SpawnPoint) {
			this.parent.stroke(255,255,255);
			this.parent.rect(t.getX(),t.getY(),TileObject.TILE_SIZE,TileObject.TILE_SIZE);
			return true;
		}
		return false;
	}

	PApplet parent;
}

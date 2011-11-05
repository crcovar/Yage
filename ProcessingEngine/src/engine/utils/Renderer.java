package engine.utils;

import engine.GameObject;
import engine.events.*;
import engine.tileobject.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

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
		PImage tilemap = this.parent.loadImage("assets/tilemap.png");
		
		// only support a single row tilemap right now
		this.tiles = new PImage[tilemap.width/TileObject.TILE_SIZE];
		tilemap.loadPixels();
		
		// build out the array of tiles from the tilemap
		for(int i=0; i<this.tiles.length;i++) {
			this.tiles[i] = this.parent.createImage(TileObject.TILE_SIZE, TileObject.TILE_SIZE, PConstants.RGB);
			this.tiles[i].loadPixels();
			for(int y=0;y<TileObject.TILE_SIZE;y++) {
				for(int x=0;x<TileObject.TILE_SIZE;x++) {
					this.tiles[i].pixels[x+y * this.tiles[i].width] = tilemap.pixels[(x + i*TileObject.TILE_SIZE) + y * tilemap.width];
				}
			}
			
			this.tiles[i].updatePixels();
		}
		
		// register our events
		EventManager.getInstance().registerListener(this, "draw");
		EventManager.getInstance().registerListener(this, "clear");
		EventManager.getInstance().registerListener(this, "text");
		EventManager.getInstance().registerListener(this, "selectedtext");
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventData event) {
		if(name.equals("clear")) {
			this.clear();
			return true;
		} else if(name.equals("draw")) {
			if(event.getMessage().toLowerCase().equals("player")) {
				this.drawEllipse(event.getX(), event.getY(), event.getWidth(), event.getHeight());
				return true;
			}
			else
				return this.draw(event.getMessage(), event.getX(), event.getY(), event.getWidth(), event.getHeight());
		} else if(name.equals("text") && event != null) {
			this.text(event.getMessage(), event.getX(), event.getY());
			return true;
		} else if(name.equals("selectedtext") && event != null) {
			this.selectedText(event.getMessage(), event.getX(), event.getY());
			return true;
		}
		return false;
	}
	
	/**
	 * Used for processing the clear event. Draws the screen black
	 */
	private void clear() {
		this.parent.background(34,155,221);
	}
	
	private void drawEllipse(int x, int y, int w, int h) {
		parent.stroke(255,255,255);
		parent.fill(255,255,255);
		parent.ellipse(x,y,w,h);
	}
	
	private boolean draw(String tileObject, int x, int y, int w, int h) {
		String s = tileObject.toLowerCase();
		
		int tileIndex = 0;
		
		if (s.equals("platform")) {
			tileIndex = 0;
		} else if(s.equals("deathzone")) {
			tileIndex = 1;
		} else if (s.equals("spawnpoint")) {
			tileIndex = 2;
		} else if (s.equals("victoryzone")) {
			tileIndex = 3;
		} else
			return false;
		
		for(int i=0; i<w; i++){
			for(int j=0;j<h;j++) {
				this.parent.image(this.tiles[tileIndex], x*TileObject.TILE_SIZE + (i*TileObject.TILE_SIZE),y*TileObject.TILE_SIZE + (j*TileObject.TILE_SIZE));
			}
		}
		
		return true;
	}
	
	private void text(String text, int x, int y) {
		this.parent.fill(255,255,255);
		this.parent.text(text,x,y);
	}
	
	private void selectedText(String text, int x, int y) {
		this.parent.fill(255,255,0);
		this.parent.text(text,x,y);
	}

	private PApplet parent;
	private PImage[] tiles;
}

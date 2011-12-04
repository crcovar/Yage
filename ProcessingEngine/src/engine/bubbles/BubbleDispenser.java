/**
 * 
 */
package engine.bubbles;

import engine.GameObject;
import engine.Level;
import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.TileObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class BubbleDispenser extends GameObject implements TileObject {

	public BubbleDispenser() {
		this.x = 0;
		this.y = 0;
		this.t_height = 1;
		this.t_width = 40;
		
		this.velocityX = 0;
		
		this.atBat = BubbleState.values()[(int) (Math.random()*4)];
		this.onDeck = BubbleState.values()[(int) (Math.random()*4)];
		
		EventManager.getInstance().registerListener(this, "move");
	}
	
	public Bubble launchBubble() {
		Bubble b = new Bubble();
		b.setX(this.x*TileObject.TILE_SIZE +TileObject.TILE_SIZE);
		b.setY(this.y*TileObject.TILE_SIZE - TileObject.TILE_SIZE);
		b.setVelocityY((short) -(b.MAX_VELOCITY / 2));
		b.setVelocityX((short) this.velocityX);
		b.setParam("flag", "" + atBat.ordinal());
		b.setParam("free", "true");
		
		atBat = onDeck;
		onDeck = BubbleState.values()[(int) (Math.random()*4)];
		
		return b;
	}
	
	public void setVelocityX(int v) {
		if(v < -TileObject.TILE_SIZE)
			this.velocityX = -TileObject.TILE_SIZE;
		else if(v > TileObject.TILE_SIZE)
			this.velocityX = TileObject.TILE_SIZE;
		else
			this.velocityX = v;
	}
	
	public int getVelocityX() {
		return this.velocityX;
	}
	
	@Override
	public boolean collide(Player p) {
		return false;
	}

	@Override
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("BubbleDispenser",this.x,this.y,this.t_width,this.t_height));
		
		EventData atBatData = new EventData("Bubble", this.x*TileObject.TILE_SIZE + TileObject.TILE_SIZE, this.y*TileObject.TILE_SIZE + TileObject.TILE_SIZE*2);
		atBatData.setParam("value","" + atBat.ordinal());
		EventManager.getInstance().sendEvent("draw", atBatData);
		
		EventData onDeckData = new EventData("Bubble", this.x*TileObject.TILE_SIZE + TileObject.TILE_SIZE*3, this.y*TileObject.TILE_SIZE + TileObject.TILE_SIZE*2);
		onDeckData.setParam("value","" + onDeck.ordinal());
		EventManager.getInstance().sendEvent("draw", onDeckData);
		
		EventManager.getInstance().sendEvent("text", new EventData(""+velocityX,this.x*TileObject.TILE_SIZE - TileObject.TILE_SIZE, this.y*TileObject.TILE_SIZE + TileObject.TILE_SIZE));
	}

	@Override
	public boolean setParam(String name, String value) {
		String n = name.toLowerCase();
		int v;
		
		try {
			v = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(n.equals("x")) {
			this.x = v;
			return true;
		} else if(n.equals("y")) {
			this.y = v;
			return true;
		} else if(n.equals("z")) {
			return false;
		} else if(n.equals("width")) {
			this.t_width = v;
			return true;
		} else if(n.equals("height")) {
			this.t_height = v;
			return true;
		}
		
		return false;
	}

	@Override
	public String printParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getX() { return this.x; }

	@Override
	public int getY() { return this.y; }

	@Override
	public int getWidth() {	return this.t_width; }

	@Override
	public int getHeight() { return this.t_height; }
	
	public boolean adjustCannon(short direction) {
		if(direction == Level.LEFT)
			this.setVelocityX(velocityX-1);
		else if(direction == Level.RIGHT)
			this.setVelocityX(velocityX+1);
		else
			return false;
		return true;
	}
	
	public boolean processMessage(String name, EventData event) {
		if(name.equals("move")) {
			return adjustCannon((short) event.getValue());
		}
		return false;		
	}

	private int x;
	private int y;
	private int t_width;
	private int t_height;
	
	private int velocityX;
	
	private BubbleState atBat;
	private BubbleState onDeck;
}

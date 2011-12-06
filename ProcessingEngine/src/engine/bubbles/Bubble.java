package engine.bubbles;

import engine.Level;
import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.TileObject;
import engine.utils.ScriptingEngine;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Bubble extends Player implements TileObject {
	public static int top = Integer.MAX_VALUE;
	public static int dropTimer;
	
	/**
	 * Default Constructor
	 */
	public Bubble() {
		super();
	    this.radius = 16;
		this.color = BubbleState.RED;
		this.free = true;
		//this.bubble = null;
	}

	public void setX(int x) {
		this.centerX = x;
	}
	
	public void setY(int y) {
		this.centerY = y;
	}
	
	@Override
	public int getX() {
		return this.centerX;
	}

	@Override
	public int getY() {
		return this.centerY;
	}
	
	@Override
	public int getWidth() { return 1; }

	@Override
	public int getHeight() { return 1; }

	public void moveDown() {
		this.centerY+=TileObject.TILE_SIZE;
	}

	public int getTopBound() { return (centerY-this.radius); }
	public int getSmallTopBound() { return centerY - this.radius + 2; }
	public int getBottomBound() { return (centerY+this.radius); }
	public int getSmallBottomBound() { return centerY + this.radius - 2; }
	public int getLeftBound() { return centerX-this.radius; }
	public int getSmallLeftBound() { return centerX - this.radius + 2; }
	public int getRightBound() { return centerX + this.radius; }
	public int getSmallRightBound() { return centerX + this.radius - 2; }
	
	@Override
	public void collideTop(int bound) {
		ScriptingEngine.getInstance().invokeFunction("holdY", this);
	}
	
	@Override
	public void collideLeft(int bound) {
		centerX = bound+this.radius;
		this.velocityX = (short) -this.velocityX;
		
	}

	@Override
	public void collideRight(int bound) {
		centerX = bound-this.radius;
		this.velocityX = (short) -this.velocityX;
		
	}
	
//	public boolean popBubble(Bubble bubble, LinkedList<Bubble> bubbles) {
//		return false;
//	}
	
	
	@Override
	public void death() {
		return;
	}
	
	public void gravity() {
		ScriptingEngine.getInstance().invokeFunction("inverseGravity", this, Bubble.top);
	}
	
	public boolean isFree() { 
		return this.free;
	}

	@Override
	public void update() {
		gravity();

	    centerX += velocityX;
	    centerY += velocityY;
	}
	
	@Override
	public void draw() {
		EventData data = new EventData("Bubble", centerX,centerY);
		data.setParam("value", ""+color.ordinal());
		EventManager.getInstance().sendEvent("draw", data);
	}
	
	@Override
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean collide(Bubble b) {
		// check to make sure this isn't a loose bubble.
		if(this.isFree())
			return false;
		
		int a, dx, dy;
		a = (b.radius+this.radius) * (b.radius+this.radius);
		dx = this.centerX - b.centerX;
		dy = this.centerY - b.centerY;
		
		if(a > (dx*dx) + (dy*dy)) {
			if(this.centerX <= b.centerX)
			{
				b.centerX = this.centerX + this.radius;
				b.centerY = this.centerY + this.radius * 2;
			} else {
				b.centerX = this.centerX - this.radius;
				b.centerY = this.centerY + this.radius * 2;
			}
			b.velocityX = b.velocityY = 0;
			b.free = false;
			
//			b.bubble = this;
			
			return true;
		} else
			return false;
		
	}

	@Override
	public boolean setParam(String name, String value) {
		String n = name.toLowerCase();
		int v;
		
		if(n.equals("free")) {
			if(value.equals("true"))
				this.free = true;
			else
				this.free = false;
			return true;
		}
		
		try {
			v = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(n.equals("x")) {
			this.centerX = v*(TileObject.TILE_SIZE*2);
			return true;
		} else if(n.equals("y")) {
			this.centerY = v*(TileObject.TILE_SIZE*2);
			if(v % 2 == 0) {
				this.centerX+=this.radius;
			}
			
			if(this.centerY <= Bubble.top) {
				Bubble.top = this.centerY;
				this.free = false;
			}
			
			return true;
		} else if(n.equals("z")) {
			this.z = v*(TileObject.TILE_SIZE*2);
			return true;
		} else if(n.equals("flag")) {
			switch(v) {
			case 0:
				this.color = BubbleState.RED;
				break;
			case 1:
				this.color = BubbleState.YELLOW;
				break;
			case 2:
				this.color = BubbleState.GREEN;
				break;
			case 3:
				this.color = BubbleState.BLUE;
				break;
			default: break;
			}
			return true;
		} else
			return super.setParam(name, value);
	}

	@Override
	public String printParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private BubbleState color;
	private boolean free;
	//private Bubble bubble;
	
	public final short MAX_VELOCITY = 16;
}

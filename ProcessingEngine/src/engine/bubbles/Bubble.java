package engine.bubbles;

import engine.Level;
import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.utils.ScriptingEngine;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Bubble extends Player implements TileObject {
	
	/**
	 * Default Constructor
	 */
	public Bubble() {
		super();
	    this.radius = 16;
		this.color = BubbleState.RED;
		
		this.movement = new boolean[4];
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


	@Override
	public void gravity() {
		ScriptingEngine.getInstance().invokeFunction("inverseGravity",this);
		movement[Level.UP] = true;
	}

	public int getTopBound() { return (centerY-this.radius); }
	public int getSmallTopBound() { return centerY - this.radius/4; }
	public int getBottomBound() { return (centerY+this.radius); }
	public int getSmallBottomBound() { return centerY + this.radius/4; }
	public int getLeftBound() { return centerX-this.radius; }
	public int getSmallLeftBound() { return centerX - this.radius/4; }
	public int getRightBound() { return centerX + this.radius; }
	public int getSmallRightBound() { return centerX + this.radius/4; }
	
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
	
	@Override
	public void death() {
		return;
	}

	@Override
	public void update() {
		gravity();
		if(!movement[Level.LEFT] && velocityX < 0) velocityX++;
	    if(!movement[Level.RIGHT] && velocityX > 0) velocityX--;
	    if(!movement[Level.UP] && velocityY < 0) velocityY++;
	    
	    centerX += velocityX;
	    centerY += velocityY;  
	  
	    movement[Level.LEFT] = false;
	    movement[Level.RIGHT] = false;
	    movement[Level.UP] = false;		
	}
	
	@Override
	public void draw() {
		EventData data = new EventData("Bubble", centerX,centerY);
		data.setParam("value", ""+color.ordinal());
		EventManager.getInstance().sendEvent("draw", data);
	}
		
	private BubbleState color;
	public final short MAX_VELOCITY = 8;
	
	@Override
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
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
			this.centerX = v*(TileObject.TILE_SIZE*2);
			return true;
		} else if(n.equals("y")) {
			this.centerY = v*(TileObject.TILE_SIZE*2);
			if(v % 2 == 0) {
				this.centerX+=this.radius;
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
}

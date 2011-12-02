package engine.character;

import engine.Level;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.utils.BubbleState;
import engine.utils.ScriptingEngine;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Bubble implements Character, TileObject {
	
	/**
	 * Default Constructor
	 */
	public Bubble() {
		this.x = 0;
		this.y = 0;
	    this.velocityX = 0;
	    this.velocityY = 0;
	    this.radius = 8;
		this.color = BubbleState.RED;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getRadius() { return this.radius; }
	
	@Override
	public int getWidth() { return 1; }

	@Override
	public int getHeight() { return 1; }

	public int getVelocityX() { return this.velocityX; }
	public int getVelocityY() { return this.velocityY; }
	
	/**
	 * Sets the value of the velocity along the X axis
	 * @param vx velocity to set, Must fall within range of <code>MAX_VELOCITY</code>
	 */
	public void setVelocityX(short vx) {
		if(vx < -MAX_VELOCITY)
			this.velocityX = -MAX_VELOCITY;
		else if(vx > MAX_VELOCITY)
			this.velocityX = MAX_VELOCITY;
		else
			this.velocityX = vx;
	}
	
	/**
	 * Sets the value of the velocity along the Y axis
	 * @param vy velocity to set, Must fall within range of <code>MAX_VELOCITY</code>
	 */
	public void setVelocityY(short vy) {
		if(vy < -MAX_VELOCITY)
			this.velocityY = -MAX_VELOCITY;
		else if(vy > MAX_VELOCITY)
			this.velocityY = MAX_VELOCITY;
		else
			this.velocityY = vy;
	}
	
	@Override
	public void setSpawn(SpawnPoint s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gravity() {
		ScriptingEngine.getInstance().invokeFunction("float", this);
		
	}

	public int getTopBound() { return (y-this.radius); }
	public int getSmallTopBound() { return y - this.radius/2; }
	public int getBottomBound() { return (y+this.radius); }
	public int getSmallBottomBound() { return y + this.radius/2; }
	public int getLeftBound() { return x-this.radius; }
	public int getSmallLeftBound() { return x - this.radius/2; }
	public int getRightBound() { return x + this.radius; }
	public int getSmallRightBound() { return x + this.radius/2; }
	
	@Override
	public void collideLeft(int bound) {
		x = bound+this.radius;
		this.velocityX = -this.velocityX;
		
	}

	@Override
	public void collideRight(int bound) {
		x = bound-this.radius;
		this.velocityX = -this.velocityX;
		
	}

	@Override
	public void collideTop(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collideBottom(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		gravity();
		if(!movement[Level.LEFT] && velocityX < 0) velocityX++;
	    if(!movement[Level.RIGHT] && velocityX > 0) velocityX--;
	    if(!movement[Level.UP] && velocityY < 0) velocityY++;
	    
	    x += velocityX;
	    y += velocityY;  
	  
	    movement[Level.LEFT] = false;
	    movement[Level.RIGHT] = false;
	    movement[Level.UP] = false;		
	}
	
	@Override
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("Bubble",x,y));
	}
	
	private Bubble topLeft,
				   topRight,
				   left,
				   right,
				   bottomLeft,
				   bottomRight;
	
	private int x, y;
	private String name;
	private int velocityX;
	private int velocityY;
	private boolean[] movement;
	private int z;
	private short radius;
	
	private BubbleState color;
	public final short MAX_VELOCITY = 8;
	@Override
	public boolean collide(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setParam(String name, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String printParams() {
		// TODO Auto-generated method stub
		return null;
	}
}

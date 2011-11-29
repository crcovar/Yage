package engine.character;


import engine.GameObject;
import engine.Level;
import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.SpawnPoint;
import engine.utils.ScriptingEngine;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Write java doc for boundary methods.
 * TODO: Remove magic numbers.
 * Use constants to define movement speeds, bounding boxes, etc.
 */
public class Player extends GameObject implements Character {
	
	/**
	 * Constructor, takes in PApplet object for rendering
	 * @param p PApplet that runs the graphics and input
	 */
	public Player() {
		super();
		
		//this.localEvents = false;
		
	    this.movement = new boolean[4];
	    this.direction = RIGHT;
	    this.velocityX = 0;
	    this.velocityY = 0;
	    this.radius = 8;
	    this.jumpTimer = MAX_JUMP;
	    this.spawn = null;
	    this.centerX = 16; //spawn.getX() + this.radius;
	    this.centerY = 16; //spawn.getY() + this.radius;
	    this.color = (int) (this.gUId*System.currentTimeMillis());
	    this.name = "";
	    
	    this.eventManager = EventManager.getInstance();	    
	}
	
	/**
	 * Set instance variable of game object
	 * @param name name of the instance variable
	 * @param value Value of the parameter to set
	 * @return true if the parameter exists and value was set correctly
	 */
	public boolean setParam(String name, String value) {
		String n = name.toLowerCase();
		short v;
		
		if(n.equals("name")) {
			this.name = value;
			ScriptingEngine.getInstance().addObject(this.name, this);
			return true;
		}
		
		try {
			v = Short.parseShort(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		
		if(n.equals("x")) {
			this.centerX = v;
			return true;
		} else if(n.equals("y")) {
			this.centerY = v;
			return true;
		} else if(n.equals("radius")) {
			this.radius = v;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Prints the parameters of the Player
	 * @return String of Player's parameters parsable by the scripting engine
	 * TODO: List all params.
	 */
	public String printParams() {
		return super.printParams() + " x="+this.centerX+" y="+this.centerY+" radius="+this.radius;
	}
	
	public int getX() { return this.centerX; }
	public int getY() { return this.centerY; }
	public int getRadius() { return this.radius; }
	public String getName() { return this.name; }
	public short getVelocityX() { return this.velocityX; }
	public short getVelocityY() { return this.velocityY; }
	
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
	
	/**
	 * Get the value of the jump timer
	 * @return
	 */
	public short getJumpTimer() { return this.jumpTimer; }
	
	/**
	 * Sets the value of the jump timer
	 * @param v
	 */
	public void setJumpTimer(short v) {
		if(v < 0)
			this.jumpTimer = 0;
		else if(v > MAX_JUMP)
			this.jumpTimer = MAX_JUMP;
		else
			this.jumpTimer = v;
	}
	
	/**
	 * Set the player's current spawn point
	 * @param s
	 */
	public void setSpawn(SpawnPoint s) {
		this.spawn = s;
	}
	
	/**
	 * Fetch the <code>SpawnPoint</code> currently assigned to the <code>Player</code>
	 * @return
	 */
	public SpawnPoint getSpawn() {
		return this.spawn;
	}
	
	/**
	 * Move the player to his current spawn point, used when player dies,
	 * or level restarts.
	 */
	public void moveToSpawn() {
		centerX = spawn.getX() + this.radius;
		centerY = spawn.getY() + this.radius;
	}
	
	/**
	 * Kills the player. Currently moves them to current spawn
	 */
	public void death() {
		moveToSpawn();
		velocityX = velocityY = 0;
	}
	  
	/**
	 * Player reached victory. Currently same as death() something should happen
	 * here
	 */
	public void victory() {
		moveToSpawn();
		velocityX = velocityY = 0;
	}
	
	/**
	 * Moves the player to the left
	 */
	public void moveLeft() {
		ScriptingEngine.getInstance().invokeFunction("moveLeft", this);
		movement[Level.LEFT] = true;
	}
	
	/**
	 * Moves the player to the right
	 */
	public void moveRight() {
		ScriptingEngine.getInstance().invokeFunction("moveRight", this);
		movement[Level.RIGHT] = true;
	}
	
	/**
	 * Push the player down, due to gravity.
	 */
	public void gravity() {
		ScriptingEngine.getInstance().invokeFunction("gravity", this);
	}
	
	/**
	 * Move the player up provided they can still jump
	 */
	public void moveUp() {
		ScriptingEngine.getInstance().invokeFunction("jump", this);
		movement[Level.UP] = true;
	}
	
	public void moveDown() {
		ScriptingEngine.getInstance().invokeFunction("moveDown", this);
	}
	
	public int getTopBound() { return (centerY-this.radius); }
	public int getSmallTopBound() { return centerY - this.radius/2; }
	public int getBottomBound() { return (centerY+this.radius); }
	public int getSmallBottomBound() { return centerY + this.radius/2; }
	public int getLeftBound() { return centerX-this.radius; }
	public int getSmallLeftBound() { return centerX - this.radius/2; }
	public int getRightBound() { return centerX + this.radius; }
	public int getSmallRightBound() { return centerX + this.radius/2; }
	
	/**
	 * Check for collision with another <code>Player</code>
	 * @param p The other <code>Player</code>
	 * @return true if the other player hits this player
	 */
	public boolean collide(int x, int y, int r) {
		/*int a, dx, dy;
		a = (p.radius+this.radius) * (p.radius+this.radius);
		dx = this.centerX - p.centerX;
		dy = this.centerY - p.centerY;
		
		if(a > (dx*dx) + (dy*dy)) {
			// collided

			return true;
		} else
			return false;
		*/
		
		int leftBound = x - r;
		int smallLeftBound = x - r/2;
		int rightBound = x + r;
		int smallRightBound = x  + r/2;
		int topBound = y - r;
		int smallTopBound = y - r/2;
		int bottomBound = y + r;
		int smallBottomBound = y + r/2;
	    
		boolean collided = false;   
	   
		if((smallLeftBound <= this.getRightBound()) && (smallRightBound >= this.getLeftBound())) { // we're in the x
			if((bottomBound >= this.getTopBound()) && topBound < this.getTopBound()) {
				collided = true;
				this.collideTop(bottomBound);
			}
			else if((topBound <= this.getBottomBound()) && bottomBound > this.getBottomBound()) {
				collided = true;
				this.collideBottom(topBound);
			}
		}
	    
		if((smallTopBound <= this.getBottomBound()) && (smallBottomBound >= this.getTopBound())) { // we're in the y
			if((rightBound >= this.getLeftBound()) && leftBound < this.getLeftBound()) {
				collided = true;
				this.collideLeft(rightBound);
			}
			else if((leftBound <= this.getRightBound()) && rightBound > this.getRightBound()) {
				collided = true;
				this.collideRight(leftBound);
			}
		}
		 
	    
		return collided;  
	}
	
	/**
	 * What to do when the player's top collides with something 
	 * @param bound bottom of the object player hit
	 */
	public void collideTop(int bound) {
		centerY = bound+this.radius;
		jumpTimer=0;
		velocityY = 0;
	}
	
	/**
	 * What to do when the player's bottom collides with something
	 * @param bound top of the object the player hit
	 */
	public void collideBottom(int bound) {
		centerY = bound - this.radius;
		jumpTimer = MAX_JUMP;
		velocityY = 0;
	}
	
	/**
	 * What to do when the player's left side collides with something
	 * @param bound right of the object hit
	 */
	public void collideLeft(int bound) {
		centerX = bound+this.radius;
		velocityX = 0;
	}
	
	/**
	 * What to do when the player's right side collides with something
	 * @param bound left side of the object hit
	 */
	public void collideRight(int bound) {

		centerX = bound-this.radius;
		velocityX = 0;
	}
	
	/**
	 * Update logic. Applies gravity and momentum.
	 */
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
	    
	    // set the direction
	    if(this.velocityX > 0)
	    	this.direction = RIGHT;
	    else if(this.velocityX < 0)
	    	this.direction = LEFT;
	}
	
	/**
	 * All the rendering calls for the Player
	 */
	public void draw() {
		if(spawn != null)
			spawn.draw();
		this.eventManager.sendEvent("draw",new EventData("player",this.color, this.centerX, this.centerY,this.radius*2,this.radius*2));
	}
	
	private EventManager eventManager;
	
	private String name;
	private short velocityX;
	private short velocityY;
	private int color;
	private boolean[] movement;
	private byte direction; // true if facing right
	private int centerX;
	private int centerY;
	private short radius;
	public short jumpTimer;
	
	private SpawnPoint spawn;
	
	private final byte RIGHT = 0;
	private final byte LEFT = 1;
	
	public final short MAX_JUMP = 8;
	public final short MAX_VELOCITY = 8;
}

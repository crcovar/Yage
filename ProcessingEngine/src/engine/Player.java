package engine;

import engine.tileobject.SpawnPoint;

import processing.core.PApplet;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Player {
	public Player(PApplet p) {
	    this.movement = new boolean[4];
	    this.velocityX = 0;
	    this.velocityY = 0;
	    this.radius = 8;
	    this.jumpTimer = MAX_JUMP;
	    this.spawn = null;
	    this.centerX = 16;//spawn.getX() + this.radius;
	    this.centerY = 16; //spawn.getY() + this.radius;
	    this.parent = p;
	}
	  
	public void setSpawn(SpawnPoint s) {
		this.spawn = s;
	}
	
	public void moveToSpawn() {
		centerX = spawn.getX() + this.radius;
		centerY = spawn.getY() + this.radius;
	}
	  
	public void death() {
		moveToSpawn();
		velocityX = velocityY = 0;
	}
	  
	public void victory() {
		moveToSpawn();
		velocityX = velocityY = 0;
	}
	
	public void moveLeft() {
		if(velocityX > -8) velocityX--;
		movement[Level.LEFT] = true;
	}
	
	public void moveRight() {
		if(velocityX < 8) velocityX++;
		movement[Level.RIGHT] = true;
	}
	  
	public void gravity() {
		if(velocityY < 8) velocityY++;
	}
	
	public void moveUp() {
		if(velocityY > -8 && jumpTimer > 0) { 
			jumpTimer--;
			velocityY-=2; 
		}
		movement[Level.UP] = true;
	}
	
	public int getTopBound() { return (centerY-this.radius); }
	public int getSmallTopBound() { return centerY - this.radius/2; }
	public int getBottomBound() { return (centerY+this.radius); }
	public int getSmallBottomBound() { return centerY + this.radius/2; }
	public int getLeftBound() { return centerX-this.radius; }
	public int getSmallLeftBound() { return centerX - this.radius/2; }
	public int getRightBound() { return centerX + this.radius; }
	public int getSmallRightBound() { return centerX + this.radius/2; }
	
	public void collideTop(int bound) {
		centerY = bound+this.radius;
		jumpTimer=0;
		velocityY = 0;
	}
	
	public void collideBottom(int bound) {
		centerY = bound - this.radius;
		jumpTimer = MAX_JUMP;
		velocityY = 0;
	}
	
	public void collideLeft(int bound ) {
		centerX = bound+this.radius;
		velocityX = 0;
	}
	
	public void collideRight(int bound) {
		centerX = bound-this.radius;
		velocityX = 0;
	}

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
	
	public void draw() {
		spawn.draw();
		parent.stroke(255,255,255);
		parent.fill(255,255,255);
		parent.ellipse(centerX,centerY,this.radius*2,this.radius*2);
	}

	private int velocityX;
	private int velocityY;
	private boolean[] movement;
	private int centerX;
	private int centerY;
	private int radius;
	private int jumpTimer;
	
	private SpawnPoint spawn;
	
	private PApplet parent;
	 
	private final int MAX_JUMP = 8;
}

package engine;
import processing.core.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class ProcessingSketch extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6142886135378656379L;

	public void setup() {
		size(640,480, P2D);  // screen size of 640x480 gives 40x30 tilemap
		frameRate(30);
		  
		player = new Player(this);
		game = new Game("csc481",player,this);
		//currentLevel = new Level(player,"scripts/level1",this);
		//currentLevel.startLevel();
		currentLevel = game.nextLevel();
	}
	
	public boolean checkKey(int k) { 
		if (keys.length >= k) {
			return keys[k];
		}
		return false;
	}

	public void keyPressed() {
		keys[keyCode] = true;
	}

	public void keyReleased() {
		keys[keyCode] = false;
	}
	
	public void draw() {
		if(checkKey('a') || checkKey('A')) {
		    currentLevel.movePlayer(Level.LEFT);
		}
		if(checkKey('d') || checkKey('D')) {
		    currentLevel.movePlayer(Level.RIGHT);
		}
		if(checkKey(' ')) currentLevel.movePlayer(Level.UP);
		  
		background(0,0,0);
		currentLevel.update();
		if(currentLevel.reachedVictory())
			currentLevel = game.nextLevel();
		currentLevel.draw();
	}
	
	public static void main(String [] args) {
		PApplet.main(new String[] { "engine.ProcessingSketch" });
	}
	
	private Player player;
	private Game game;
	private Level currentLevel;

	private boolean[] keys = new boolean[526];

}

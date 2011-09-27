package engine;
import engine.level.Level;
import processing.core.*;

/**
 * @author Charles Covar
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
		level1 = new Level(player,"scripts/level1",this);
		level1.startLevel();
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
		    level1.movePlayer(Level.LEFT);
		}
		if(checkKey('d') || checkKey('D')) {
		    level1.movePlayer(Level.RIGHT);
		}
		if(checkKey(' ')) level1.movePlayer(Level.UP);
		  
		background(0,0,0);
		level1.update();
		level1.draw();
	}
	
	public static void main(String [] args) {
		PApplet.main(new String[] { "engine.ProcessingSketch" });
	}
	
	private Player player;
	private Level level1;

	private boolean[] keys = new boolean[526];

}

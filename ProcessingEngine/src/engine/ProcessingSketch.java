package engine;
import engine.character.Player;
import engine.events.EventManager;
import engine.network.*;
import engine.utils.Logger;
import engine.utils.Recorder;
import engine.utils.Replay;
import processing.core.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: use the EventManager to better modularize the subsystems 
 */
public class ProcessingSketch extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6142886135378656379L;

	/**
	 * Sets the screen size, locks the framerate and initializes values.
	 */
	public void setup() {
		size(640,480, P2D);  // screen size of 640x480 gives 40x30 tilemap
		frameRate(30);
		
		this.logger = new Logger();
		this.recorder = new Recorder();
		this.renderer = new Renderer(this);
		this.replay = null;
		
		player = new Player();
		game = new Game("csc481",player,this);
		
		this.server = new Server();
		this.client = new Client();

		currentLevel = game.nextLevel();
	}
	
	/**
	 * Checks to see if a key on the keyboard has been pressed
	 * @param k char of the key we want to check
	 * @return true if the key is pressed, else false
	 */
	public boolean checkKey(char k) { 
		if (keys.length >= k) {
			return keys[k];
		}
		return false;
	}

	/**
	 * Event trigger. When a key is pressed the value of the key is set to true in our array
	 */
	public void keyPressed() {
		keys[keyCode] = true;
	}

	/**
	 * Event trigger. Sets the value of the key in our array to false.
	 */
	public void keyReleased() {
		keys[keyCode] = false;
	}
	
	/**
	 * Main game loop. Processing will call this method as often as the frame rate calls for it.
	 */
	public void draw() {
		if(checkKey('a') || checkKey('A')) {
		    currentLevel.movePlayer(Level.LEFT);
		}
		if(checkKey('d') || checkKey('D')) {
		    currentLevel.movePlayer(Level.RIGHT);
		}
		if(checkKey(' ')) currentLevel.movePlayer(Level.UP);
		
		EventManager.getInstance().sendEvent("clear", null);
		if(currentLevel.reachedVictory()) {
			if(this.replay == null)
				this.replay = new Replay("replay" + this.recorder.gUId);
			if(!this.replay.isDone()) {
				fill(255,255,255);
				text("REPLAY",10,20);
				// update and draw the replay
				this.replay.update();
				this.replay.draw();
			} else
			currentLevel = game.nextLevel();
		} else {
			currentLevel.update();
			currentLevel.draw();
		}
	}
	
	/**
	 * Main method of the engine. Creates a new Processing Applet and passes our ProcessingSketch class
	 * @param args
	 */
	public static void main(String [] args) {
		PApplet.main(new String[] { "engine.ProcessingSketch" });
	}
	
	private Player player;
	private Game game;
	private Level currentLevel;
	private Logger logger;
	private Recorder recorder;
	private Renderer renderer;
	private Replay replay;

	private Server server;
	private Client client;
	
	private boolean[] keys = new boolean[526];

}

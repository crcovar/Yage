package engine;

import engine.character.Player;
import engine.events.*;
import engine.menu.DirList;
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
		
		this.eventManager = EventManager.getInstance();
		
		this.logger = new Logger();
		this.recorder = new Recorder();
		this.renderer = new Renderer(this);
		this.replay = null;
		
		player = new Player();
		//game = new Game("csc481",player);
		
		this.server = new Server();
		this.client = new Client();

		//currentLevel = game.nextLevel();
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
	
	public void resetKeys() {
		for(int i=0;i<this.keys.length;i++)
			this.keys[i] = false;
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
		if((keyCode == 't' || keyCode == 'T') && gameState == GAME_STATE_REPLAY)
			this.replay.toggleSpeed();
		if(keyCode == ' ') {
			if(gameState == GAME_STATE_MENU && checkKey(' ')) {
				this.game = new Game(DirList.getInstance().getSelected(), this.player);
				this.currentLevel = game.nextLevel();
				gameState = GAME_STATE_LEVEL;
				resetKeys();
			} else if(gameState == GAME_STATE_END && checkKey(' ')) {
				gameState = GAME_STATE_MENU;
				resetKeys();
			}
		}
		if(keyCode == 's' || keyCode == 'S' && gameState == GAME_STATE_MENU) {
			DirList.getInstance().nextMenuItem();
		}
		if(keyCode == 'w' || keyCode == 'W' && gameState == GAME_STATE_MENU) {
			DirList.getInstance().previousMenuItem();
		}
		
		keys[keyCode] = false;
	}
	
	/**
	 * Main game loop. Processing will call this method as often as the frame rate calls for it.
	 */
	public void draw() {
		this.eventManager.sendEvent("clear", null);
				
		switch (gameState) {
		case GAME_STATE_MENU:
			DirList.getInstance().draw();			
			break;
		case GAME_STATE_LEVEL:
			if(currentLevel.reachedVictory()) {
				gameState = GAME_STATE_REPLAY;
				resetKeys();
				break;
			}
			
			if(checkKey('a') || checkKey('A')) {
			    currentLevel.movePlayer(Level.LEFT);
			}
			if(checkKey('d') || checkKey('D')) {
			    currentLevel.movePlayer(Level.RIGHT);
			}
			if(checkKey(' ')) currentLevel.movePlayer(Level.UP);
			
			currentLevel.update();
			
			this.eventManager.sendEvent("text", new RenderEvent("LIVE",10,20));
			
			currentLevel.draw();
			break;
		case GAME_STATE_REPLAY:
			if(this.replay == null)
				this.replay = new Replay("replay" + this.recorder.gUId);
			if(!this.replay.isDone()) {
				// update and draw the replay
				if(this.replay.getSpeed() == Replay.HALF) {
					frameRate(15);
					this.eventManager.sendEvent("text", new RenderEvent("REPLAY 1/2x",10,20));
				}else if (this.replay.getSpeed() == Replay.NORMAL) {
					frameRate(30);
					this.eventManager.sendEvent("text", new RenderEvent("REPLAY 1x",10,20));
				} else if (this.replay.getSpeed() == Replay.DOUBLE) {
					frameRate(60);
					this.eventManager.sendEvent("text", new RenderEvent("REPLAY 2x",10,20));
				}
				this.replay.update();
				this.replay.draw();
			} else {
				frameRate(30);
				currentLevel = game.nextLevel();
				if(currentLevel == null) {
					gameState = GAME_STATE_END;
				}
				else
					gameState = GAME_STATE_LEVEL;
				resetKeys();
			}
			break;
		case GAME_STATE_END:
			this.eventManager.sendEvent("text", new RenderEvent("YOU WIN",302,235));
			break;
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
	
	private short gameState = 0;
	private static final short GAME_STATE_MENU = 0;
	private static final short GAME_STATE_LEVEL = 1;
	private static final short GAME_STATE_REPLAY = 2;
	private static final short GAME_STATE_END = 3;
	
	private EventManager eventManager;
	@SuppressWarnings("unused")
	private Logger logger;
	private Recorder recorder;
	@SuppressWarnings("unused")
	private Renderer renderer;
	private Replay replay;

	@SuppressWarnings("unused")
	private Server server;
	@SuppressWarnings("unused")
	private Client client;
	
	private boolean[] keys = new boolean[526];

}

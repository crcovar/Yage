/**
 * 
 */
package tools;

import processing.core.PApplet;
import engine.Game;
import engine.GameObject;
import engine.Level;
import engine.character.Player;
import engine.events.Event;
import engine.events.EventData;
import engine.events.EventManager;
import engine.menu.DirList;
import engine.network.Connection;
import engine.utils.Logger;
import engine.utils.Recorder;
import engine.utils.Renderer;
import engine.utils.Replay;

/**
 * @author Covar
 *
 */
public class Peer extends PApplet {
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
		new Recorder();
		this.renderer = new Renderer(this);
		this.replay = null;
		
		player = new Player();
		this.connection = null;
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
		
		if(keyCode == 'g' || keyCode == 'G' && gameState == GAME_STATE_LEVEL) {
				currentLevel.addPlayer(new Player());
		}
		
		keys[keyCode] = false;
	}
	
	/**
	 * Main game loop. Processing will call this method as often as the frame rate calls for it.
	 */
	public void draw() {	
		GameObject.gameTime++;	// this should be the only write to this variable
		
		this.eventManager.sendEvent("clear", null);
				
		switch (gameState) {
		case GAME_STATE_MENU:
			DirList.getInstance().draw();			
			break;
		case GAME_STATE_LEVEL:
			if(connection == null) {
				connection = new Connection("localhost", 10040);
				this.connection.send(new Event("null",null));
			}
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
			
			this.eventManager.sendEvent("text", new EventData("LIVE",10,20));
			
			currentLevel.draw();
			break;
		case GAME_STATE_REPLAY:
			if(this.replay == null)
				this.replay = new Replay("replay");
			if(!this.replay.isDone()) {
				// update and draw the replay
				if(this.replay.getSpeed() == Replay.HALF) {
					frameRate(15);
					this.eventManager.sendEvent("text", new EventData("REPLAY 1/2x",10,20));
				}else if (this.replay.getSpeed() == Replay.NORMAL) {
					frameRate(30);
					this.eventManager.sendEvent("text", new EventData("REPLAY 1x",10,20));
				} else if (this.replay.getSpeed() == Replay.DOUBLE) {
					frameRate(60);
					this.eventManager.sendEvent("text", new EventData("REPLAY 2x",10,20));
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
			this.eventManager.sendEvent("text", new EventData("YOU WIN",302,235));
			break;
		}

		this.eventManager.process();
	}
	
	/**
	 * Main method of the engine. Creates a new Processing Applet and passes our ProcessingSketch class
	 * @param args
	 */
	public static void main(String [] args) {
		PApplet.main(new String[] { "tools.Peer" });
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
	@SuppressWarnings("unused")
	private Renderer renderer;
	private Replay replay;
	
	private Connection connection;
	
	private boolean[] keys = new boolean[526];

}

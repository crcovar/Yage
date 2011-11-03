package engine;

import java.io.*;
import java.util.LinkedList;

import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;

/**
 * Handles file loading of Games/mods (no mod support yet)
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Add support for mods (mod directory)
 * TODO: Support multiple players
 */
public class Game extends GameObject {
	/**
	 * Constructor
	 * @param name The name of the game to load
	 * @param player Player object who will be playing the game
	 * @param parent <code>PApplet</code> used for drawing
	 */
	public Game(String name, Player player) {		
		this.name = name;
		this.levels = new LinkedList<String>();
		this.player = player;
		this.eventManager = EventManager.getInstance();

		File dir = new File("games/"+name);
		FileReader reader = null;
		
		if(dir.isDirectory()) {
			for (String s : dir.list()) {
				if(s.equals(name)) {
					try {
						reader = new FileReader("games/"+name+"/"+name);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					break;
				}		
			} // end for
		} // end if
		
		if(reader == null) {
			for(String s: dir.list()) {
				this.levels.add(s);
				this.eventManager.sendEvent("log", new EventData("found level " + s));
			}
		} else {
			BufferedReader in = new BufferedReader(reader);
			try {
				String line = in.readLine();
				while(line != null) {
					this.levels.add(line);
					this.eventManager.sendEvent("log", new EventData("found level " + line));
					line = in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Pops the next level name from the stack, then reads the corresponding file
	 * and loads the level into memory
	 * @return newly constructed level, already started
	 */
	public Level nextLevel() {
		if(this.levels.isEmpty()) {
			this.eventManager.sendEvent("log", new EventData("No more Levels to load"));
			return null;
		}
		else {
			this.eventManager.sendEvent("log", new EventData("Loading next level from file..."));
			Level l = new Level(this.player, "games/"+this.name+"/"+this.levels.pop());
			l.startLevel();
			this.eventManager.sendEvent("log", new EventData("level loaded and initialized successfully"));
			return l;
		}
	}

	private EventManager eventManager;
	private String name;
	private LinkedList<String> levels;
	private Player player;

}

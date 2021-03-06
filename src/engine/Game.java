package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import engine.character.Player;
import engine.events.EventData;
import engine.events.EventManager;

/**
 * Handles file loading of Games/mods (no mod support yet)
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Add support for mods (mod directory)
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
		this.currentLevel = null;
		this.levels = new LinkedList<String>();
		this.players = new LinkedList<Player>();
		if(player != null)
			this.players.add(player);
		this.eventManager = EventManager.getInstance();
		this.eventManager.registerListener(this, "exchange");
		this.eventManager.registerListener(this, "addplayer");
		this.localEvents = false;

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
		this.eventManager.unregisterListener(this.currentLevel);
		if(this.levels.isEmpty()) {
			this.eventManager.sendEvent("log", new EventData("No more Levels to load"));
			this.currentLevel = null;
		} else {
			this.eventManager.sendEvent("log", new EventData("Loading next level from file..."));
			this.currentLevel = new Level(this.players, "games/"+this.name+"/"+this.levels.pop());
			//this.currentLevel.startLevel();
			this.eventManager.sendEvent("log", new EventData("level loaded and initialized successfully"));
		}
		
		return this.currentLevel;
	}
	
	/**
	 * Process events
	 */
	@Override
	public boolean processMessage(String name, EventData event) {
		if(name.equals("exchange")) {
			if(currentLevel != null)
				this.eventManager.sendEvent("buildlevel", new EventData(this.currentLevel.toString()));
			return true;
		}
		if(name.equals("addplayer")) {
			for(Player p : players)
				if(p.getName().equals(event.getMessage()))
					return false;

			Player p = new Player();
			p.setParam("name", event.getMessage());
			p.setParam("x", ""+event.getX());
			p.setParam("y", ""+event.getY());
			p.setSpawn(this.currentLevel.getSpawn());
			this.players.add(p);
			p.moveToSpawn();
			return true;
		}
		return false;
	}

	private EventManager eventManager;
	private String name;
	private Level currentLevel;
	private LinkedList<String> levels;
	private LinkedList<Player> players;

}

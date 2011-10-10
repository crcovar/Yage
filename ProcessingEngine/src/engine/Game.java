/**
 * 
 */
package engine;

import processing.core.PApplet;
import java.io.*;
import java.util.LinkedList;

import engine.character.Player;

/**
 * Handles file loading of Games/mods (no mod support yet)
 * @author Charles Covar (covar1@gmail.com)
 */
public class Game extends GameObject {
	public Game(String name, Player player, PApplet parent) {		
		this.name = name;
		this.levels = new LinkedList<String>();
		this.player = player;
		this.parent = parent;
		
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
				this.eventManager.sendEvent("log", "found level " + s);
			}
		} else {
			BufferedReader in = new BufferedReader(reader);
			try {
				String line = in.readLine();
				while(line != null) {
					this.levels.add(line);
					this.eventManager.sendEvent("log", "found level " + line);
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
			this.eventManager.sendEvent("log", "No more Levels to load");
			return null;
		}
		else {
			this.eventManager.sendEvent("log", "Loading next level from file...");
			Level l = new Level(this.player, "games/"+this.name+"/"+this.levels.pop(),this.parent);
			l.startLevel();
			this.eventManager.sendEvent("log", "level loaded and initialized successfully");
			return l;
		}
	}

	private EventManager eventManager;
	private String name;
	private LinkedList<String> levels;
	private Player player;
	private PApplet parent;

}

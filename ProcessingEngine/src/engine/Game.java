/**
 * 
 */
package engine;

import processing.core.PApplet;
import java.io.*;
import java.util.LinkedList;

/**
 * @author Charles Covar
 *
 */
public class Game {
	public Game(String name, Player player, PApplet parent) {
		this.name = name;
		this.levels = new LinkedList<String>();
		this.player = player;
		this.parent = parent;
		
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
			}
		} else {
			BufferedReader in = new BufferedReader(reader);
			try {
				String line = in.readLine();
				while(line != null) {
					this.levels.add(line);
					line = in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Level nextLevel() {
		if(this.levels.isEmpty())
			return null;
		else {
			Level l = new Level(this.player, "games/"+this.name+"/"+this.levels.pop(),this.parent);
			l.startLevel();
			return l;
		}
	}
	
	private String name;
	private LinkedList<String> levels;
	private Player player;
	private PApplet parent;

}

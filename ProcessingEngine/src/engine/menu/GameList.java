package engine.menu;

import engine.GameObject;
import engine.events.*;

import java.io.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class GameList extends GameObject {
	private GameList() {
		File dir = new File("games");
		if(dir.isDirectory())
			this.gameList = dir.list();
		this.selected = 0;
	}
	
	public void nextMenuItem() {
		this.selected = (short) ((this.selected == this.gameList.length - 1) ? 0 : this.selected + 1);
	}
	
	public void previousMenuItem() {
		this.selected = (short) ((this.selected == 0) ? this.gameList.length - 1 : this.selected - 1);
	}
	
	public void draw() {
		EventManager em = EventManager.getInstance();
		
		for(short i=0; i<this.gameList.length;i++) {
			em.sendEvent("text", new RenderEvent(this.gameList[i],50, 20+(i*20)));
		}
	}
	
	public static GameList getInstance() {
		if(instance == null)
			instance = new GameList();
		return instance;
	}
	
	private short selected;
	private String[] gameList;
	
	private static GameList instance;
}

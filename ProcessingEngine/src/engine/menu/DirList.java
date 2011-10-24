package engine.menu;

import engine.GameObject;
import engine.events.*;

import java.io.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class DirList extends GameObject {
	private DirList() {
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
	
	public String getSelected() {
		return this.gameList[this.selected];
	}
	
	public void draw() {
		EventManager em = EventManager.getInstance();
		
		for(short i=0; i<this.gameList.length;i++) {
			RenderEvent re = new RenderEvent(this.gameList[i],50,20+(i*20));
			if(i == this.selected)
				em.sendEvent("selectedtext", re);
			else
				em.sendEvent("text", re);
		}
	}
	
	public static DirList getInstance() {
		if(instance == null)
			instance = new DirList();
		return instance;
	}
	
	private short selected;
	private String[] gameList;
	
	private static DirList instance;
}

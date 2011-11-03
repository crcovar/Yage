package engine.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import engine.GameObject;
import engine.events.EventData;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Recorder extends GameObject {
	public Recorder() {
		super();
		
		EventManager.getInstance().registerListener(this, "record");
		
		out = null;
		
		try {
			out = new PrintWriter("replay"+this.gUId);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() throws Throwable {
		try {
			out.close();
		} finally {
			super.finalize();
		}
	}
	
	public boolean processMessage(String name, EventData event) {
		if(name.equals("record")) {
			return this.record(event.getMessage(), event.getObject());
		}
		
		return false;
	}
	
	private boolean record(String name, GameObject object) {
		if(out != null) {
			out.println(name + " " + object.printParams());
			out.flush();
			return true;
		}
		return false;
	}
	
	private PrintWriter out;

}

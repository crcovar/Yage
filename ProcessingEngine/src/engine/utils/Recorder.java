package engine.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import engine.GameObject;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Recorder extends GameObject {
	public Recorder() {
		super();
		
		EventManager.getInstance().registerListener("record", this);
		
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
	
	public boolean processMessage(String name, String msg) {
		if(name.equals("record")) {
			return this.record(msg);
		}
		
		return false;
	}
	
	private boolean record(String msg) {
		if(out != null) {
			out.println(msg);
			out.flush();
			return true;
		}
		return false;
	}
	
	private PrintWriter out;
}

package engine.utils;

import java.io.*;

import engine.GameObject;
import engine.events.EventMessage;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: add support for appending to the log instead of starting fresh every startup
 */
public class Logger extends GameObject {
	/**
	 * Default Constructor
	 */
	public Logger() {
		super();
		
		this.out = null;
		
		EventManager.getInstance().registerListener(this, "log");
		
		try {
			this.out = new PrintWriter(this.logFile);
			processMessage("log",new EventMessage("--Start of Log--"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Logger(String logFile) {
		super();
		
		this.out = null;
		
		EventManager.getInstance().registerListener(this, "log");
		
		try {
			this.out = new PrintWriter(logFile);
			processMessage("log",new EventMessage("--Start of Log--"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() throws Throwable {
		try {
			this.out.close();
		} finally {
			super.finalize();
		}
	}
	
	/**
	 * Overrides <code>GameObject</code>'s method.
	 * @return True if the message gets processed
	 */
	public boolean processMessage(String name, EventMessage event) {
		if(name.equals("log")) {
			log(event.getMessage());
			return true;
		}
		
		return false;
	}
	
	/**
	 * Logs a message to a file
	 * @param msg message to log
	 * @return true if writing to file is successful
	 */
	private void log(String msg) {
		if(this.out != null) {
			this.out.println(msg);
			this.out.flush();
			
			EventManager.getInstance().sendEvent("toserver", new EventMessage(msg));
		}
	}
	
	private PrintWriter out;
	
	private final String logFile = "out.log";
}

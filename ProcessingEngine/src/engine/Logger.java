/**
 * 
 */
package engine;

import java.io.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Logger extends GameObject {
	/**
	 * Default Constructor
	 */
	public Logger() {
		super();
		
		this.out = null;
		
		EventManager.getInstance().registerListener("log", this);
		try {
			this.out = new PrintWriter(logFile);
			processMessage("log","--Start of Log--");
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
	public boolean processMessage(String name, String msg) {
		if(name.equals("log")) {
			log(msg);
			return true;
		}
		return false;
	}
	
	/**
	 * Logs a message to a file
	 * @param msg message to log
	 * @return true if writing to file is successful
	 */
	public void log(String msg) {
		if(this.out != null) {
			this.out.println(msg);
			this.out.flush();
		}
	}
	
	private PrintWriter out;
	
	private final String logFile = "out.log";
}

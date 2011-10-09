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
		EventManager.getInstance().registerListener("logger", this);
	}
	
	/**
	 * Overrides <code>GameObject</code>'s method.
	 * @return True if the message gets processed
	 */
	public boolean processMessage(String name, String msg) {
		if(name.equals("logger")) {
			return log(msg);
		}
		return false;
	}
	
	/**
	 * Logs a message to a file
	 * @param msg message to log
	 * @return true if writing to file is successful
	 */
	private boolean log(String msg) {
		try {
			PrintWriter out = new PrintWriter(logFile);
			out.println(msg);
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private final String logFile = "out.log";
}

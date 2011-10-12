package engine.network;

import engine.GameObject;
import engine.events.EventManager;

import java.io.IOException;
import java.net.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: connect to Server
 * TODO: process messages from Server
 * TODO: override processMessage() to process registered events
 * TODO: send messages to Server
 * TODO: pass messages from Server to other systems
 */
public class Client extends GameObject {
	/**
	 * Default Constructor. Connects to localhost from default port
	 */
	public Client() {
		this("localhost", 10040);
	}
	
	/**
	 * Constructor to connect client to remote server and non-default port
	 * @param host
	 * @param port
	 */
	public Client(String host, int port) {
		super();
		
		EventManager.getInstance().registerListener("toserver", this);
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Destructor. Closes the socket
	 */
	public void finalize() throws Throwable {
		try {
			this.socket.close();
		} finally {
			super.finalize();
		}
	}
	
	private Socket socket;
}

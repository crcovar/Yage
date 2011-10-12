package engine.network;

import engine.GameObject;
import engine.events.EventManager;

import java.io.IOException;
import java.net.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Accept connections from clients
 * TODO: process messages from clients
 * TODO: send messages to clients
 * TODO: override processMessage() to process registered events
 * TODO: pass messages sent from clients to other systems
 */
public class Server extends GameObject {
	/**
	 * Default Constructor. Sets default port number
	 */
	public Server() {
		this(10040);
	}
	
	/**
	 * Constructor for non default port number
	 * @param port Port number for the server to listen on
	 */
	public Server(int port) {
		super();
		
		EventManager.getInstance().registerListener("toclient", this);
		
		this.serverSocket = null;
		
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Destructor. Closes the <code>ServerSocket</code>
	 */
	public void finalize() throws Throwable {
		try {
			this.serverSocket.close();
		} finally {
			super.finalize();
		}
	}
	
	private ServerSocket serverSocket;
}

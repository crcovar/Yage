package engine.network;

import engine.GameObject;
import engine.events.EventManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author Charles Covar (covar1@gmail.com)
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
		
		//register as listener for our events
		EventManager.getInstance().registerListener("toserver", this);
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
			this.input = this.socket.getInputStream();
			this.output = this.socket.getOutputStream();
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
			this.input.close();
			this.output.close();
		} finally {
			super.finalize();
		}
	}
	
	private Socket socket;
	private InputStream input;
	private OutputStream output;
}

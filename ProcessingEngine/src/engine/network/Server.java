package engine.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import engine.GameObject;
import engine.events.Event;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 */
public class Server extends GameObject implements Runnable {
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
		
		this.serverSocket = null;
		this.connections = new LinkedList<Connection>();
		
		try {
			this.serverSocket = new ServerSocket(port);
			Thread t = new Thread(this);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Destructor. Closes the <code>ServerSocket</code>
	 */
	public void finalize() throws Throwable {
		try {
			if(this.serverSocket != null)
				this.serverSocket.close();
		} finally {
			super.finalize();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Socket s = this.serverSocket.accept();
				Connection c = new Connection(s);
				c.send(new Event("netinit",null));
				Thread t = new Thread(c);
				t.start();
				//this.connections.add(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean processMessage(String name, EventData event) {
		for(Connection c : this.connections) {
				c.processMessage(name, event);
		}
		return true;
	}
	
	private ServerSocket serverSocket;
	private LinkedList<Connection> connections;

}

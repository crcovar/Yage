package engine.network;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventMessage;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: pass messages sent from clients to other systems
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
			// TODO Auto-generated catch block
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
				Thread t = new Thread(c);
				t.start();
				this.connections.add(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean processMessage(String name, EventMessage event) {
		for(Connection c : this.connections) {
				c.processMessage(name, event);
		}
		return true;
	}
	
	private ServerSocket serverSocket;
	private LinkedList<Connection> connections;

}

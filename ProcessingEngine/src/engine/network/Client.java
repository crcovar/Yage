package engine.network;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventMessage;

import java.io.*;
import java.net.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: pass messages from Server to other systems
 */
public class Client extends GameObject implements Runnable{
	/**
	 * Default Constructor. Connects to localhost from default port
	 */
	public Client() {
		this("127.0.0.1", 10040);
	}
	
	/**
	 * Constructor to connect client to remote server and non-default port
	 * @param host
	 * @param port
	 */
	public Client(String host, int port) {
		super();
		
		//register as listener for our events
		this.eventManager = EventManager.getInstance();
		this.eventManager.registerListener("toserver", this);
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
			this.input = new ObjectInputStream(this.socket.getInputStream());
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
			this.eventManager.sendEvent("log",new EventMessage("Client connected to server"));
			
			new Thread(this).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Destructor. Closes the socket
	 */
	public void finalize() throws Throwable {
		try {
			this.input.close();
			this.output.close();
			this.socket.close();
		} finally {
			super.finalize();
		}
	}
	
	public void run() {
		try {
			while(true) {
				String name = (String) this.input.readObject();
				EventMessage event = (EventMessage) this.input.readObject();
				this.eventManager.sendEvent(name, event);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean processMessage(String name, EventMessage event) {
		if(name.equals("toserver")) {
			if(this.output != null) {
				try {
					this.output.writeObject(name);
					this.output.writeObject(event);
					this.output.flush();
				} catch (IOException e) {
					this.eventManager.sendEvent("log", new EventMessage("Unable to send object to server"));
					e.printStackTrace();
				}
				
				return true;
			}
		}
		return false;
	}
	
	private EventManager eventManager;
	
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
}

package engine.network;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventData;

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
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
			try{
				this.input = new ObjectInputStream(this.socket.getInputStream());
			} catch (IOException e) {
				EventData m = new EventData("Unable to Create ObjectInputStream");
				this.eventManager.sendEvent("log", m);
				m.setMessage(e.getMessage());
				this.eventManager.sendEvent("log", m);
				this.input = null;
			}
			
			try{
				this.output = new ObjectOutputStream(this.socket.getOutputStream());
			} catch (IOException e) {
				EventData m = new EventData("Unable to Create ObjectOutputStream");
				this.eventManager.sendEvent("log", m);
				m.setMessage(e.getMessage());
				this.eventManager.sendEvent("log", m);
				this.output = null;
			}
			
			this.eventManager.sendEvent("log",new EventData("Client connected to server"));
			
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
			if(this.input != null)
				this.input.close();
			if(this.output != null)
				this.output.close();
			if(this.socket != null)
				this.socket.close();
		} finally {
			super.finalize();
		}
	}
	
	public void run() {
		try {
			while(true) {
				if(this.input != null) {
					String name = (String) this.input.readObject();
					EventData event = (EventData) this.input.readObject();
					this.eventManager.sendEvent(name, event);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean processMessage(String name, EventData event) {
		
			if(this.output != null) {
				try {
					this.output.writeObject(name);
					this.output.flush();
					this.output.writeObject(event);
					this.output.flush();
					this.eventManager.sendEvent("log",new EventData("sent " + name +" event to server"));
				} catch (IOException e) {
					this.eventManager.sendEvent("log", new EventData("Unable to send object to server"));
					e.printStackTrace();
					return false;
				}
				
				return true;
			}
		
		return false;
	}
	
	private EventManager eventManager;
	
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
}

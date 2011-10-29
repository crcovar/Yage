package engine.network;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventMessage;

import java.io.*;
import java.net.*;

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
		
		this.eventManager = EventManager.getInstance();
		this.eventManager.registerListener("toclient", this);
		
		this.serverSocket = null;
		
		this.connection = null;
		this.in = null;
		this.out = null;
		
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
			this.os.close();
			this.is.close();
			this.out.close();
			this.in.close();
			this.connection.close();
			this.serverSocket.close();
		} finally {
			super.finalize();
		}
	}
	
	@Override
	public void run() {
		if(connection == null) {
			try{
				this.connection = this.serverSocket.accept();
				this.eventManager.sendEvent("log",new EventMessage("Server recieved connection"));
				is = this.connection.getInputStream();
				this.in = new ObjectInputStream(is);
				os = this.connection.getOutputStream();
				this.out = new ObjectOutputStream(os);
				
				while(true) {
					String name = (String) this.in.readObject();
					EventMessage event = (EventMessage) this.in.readObject();
					this.eventManager.sendEvent(name, event);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean processMessage(String name, EventMessage event) {
		try {
			if(this.out != null) {
				this.out.writeObject(name);
				this.out.writeObject(event);
				this.out.flush();
				return true;
			} else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private EventManager eventManager;
	
	private ServerSocket serverSocket;
	private Socket connection;
	
	private InputStream is;
	private OutputStream os;
	private ObjectInputStream in;
	private ObjectOutputStream out;

}

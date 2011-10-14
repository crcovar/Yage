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
			this.serverSocket.close();
			this.connection.close();
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
				this.in = this.connection.getInputStream();
				this.out = this.connection.getOutputStream();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(this.in));
				while(true) {
					System.out.println(reader.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean processMessage(String name, EventMessage event) {
		if(name.equals("toclient")) {
			if(this.out != null) {
				PrintWriter pw = new PrintWriter(this.out,true);
				pw.println(event.getMessage());
				return true;
			}
		}
		return false;
	}
	
	private EventManager eventManager;
	
	private ServerSocket serverSocket;
	private Socket connection;
	
	private InputStream in;
	private OutputStream out;

}

package engine.network;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventMessage;

import java.io.*;
import java.net.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: process messages from Server
 * TODO: override processMessage() to process registered events
 * TODO: send messages to Server
 * TODO: pass messages from Server to other systems
 */
public class Client extends GameObject implements Runnable{
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
		this.eventManager = EventManager.getInstance();
		this.eventManager.registerListener("toserver", this);
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
			this.input = this.socket.getInputStream();
			this.output = this.socket.getOutputStream();
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
			this.socket.close();
			this.input.close();
			this.output.close();
		} finally {
			super.finalize();
		}
	}
	
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.input));
			while(true) {
				System.out.println(reader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean processMessage(String name, EventMessage event) {
		if(name.equals("toserver")) {
			if(this.output != null) {
				PrintWriter pw = new PrintWriter(this.output,true);
				pw.println(event.getMessage());
				return true;
			}
		}
		return false;
	}
	
	private EventManager eventManager;
	
	private Socket socket;
	private InputStream input;
	private OutputStream output;
}

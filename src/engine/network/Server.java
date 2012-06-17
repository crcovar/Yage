package engine.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import engine.GameObject;
import engine.events.Event;
import engine.events.EventData;
import engine.events.EventManager;
import engine.utils.ConfigManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 */
public class Server extends GameObject implements Runnable {
	
	/**
	 * Default Constructor checks port number from the engine.config
	 * @param port Port number for the server to listen on
	 */
	public Server() {
		super();
		int port = 10040;
		this.serverSocket = null;
		
		String p = ConfigManager.getInstance().getOption("port");
		if(p != null)
			port = Integer.parseInt(p);
		try {
			this.serverSocket = new ServerSocket(port);
			Thread t = new Thread(this);
			t.start();
		} catch (IOException e) {
			EventManager.getInstance().sendEvent("log", new EventData("Port unavailable"));
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
				c.send(new Event("netinit",new EventData("", GameObject.getGameState())));
				Thread t = new Thread(c);
				t.start();
				//this.connections.add(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	private ServerSocket serverSocket;
}

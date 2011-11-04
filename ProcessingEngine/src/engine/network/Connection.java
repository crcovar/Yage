package engine.network;

import java.net.Socket;
import java.io.*;

import engine.GameObject;
import engine.events.Event;
import engine.events.EventManager;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Connection extends GameObject implements Runnable {
	/**
	 * Constructor for using <code>Connection</code> from a client. <code>Connection</code> is now both ends of a one-to-one link between machines.
	 * Use the <code>Server</code> class if you need to maintain and track multiple connections
	 * @param host Hostname or IP of the <code>Server</code> object you want to connect to.
	 * @param port Port number the <code>Server</code> will be listening on.
	 */
	public Connection(String host, int port) {
		super();
		
		//register as listener for our events
		this.eventManager = EventManager.getInstance();
		
		this.socket = null;
		
		try {
			this.socket = new Socket(host,port);
			
			try {
				this.outputStream = this.socket.getOutputStream();
				try {
					this.out = new ObjectOutputStream(this.outputStream);
				} catch (IOException e) {
					EventData m = new EventData("Unable to get ObjectOutputStream");
					EventManager.getInstance().sendEvent("log", m);
					m.setMessage(e.getMessage());
					EventManager.getInstance().sendEvent("log", m);
					this.out = null;
				}
			} catch (IOException e) {
				EventData m = new EventData("Unable to get OutputStream");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.outputStream = null;
			}
			
			this.eventManager.sendEvent("log",new EventData("Client connected to server"));
			
			new Thread(this).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for using <code>Connection</code> from a <code>Server</code>. This method uses an already open <code>Socket</code> provided by the server.
	 * @param socket <code>Socket</code> object received by the server.
	 */
	public Connection(Socket socket) {
		super();
		
		this.socket = socket;
		
		// try building the ObjectOutputStream
		try {
			this.outputStream = this.socket.getOutputStream();
			try {
				this.out = new ObjectOutputStream(this.outputStream);
			} catch (IOException e) {
				EventData m = new EventData("Unable to get ObjectOutputStream");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.out = null;
			}
		} catch (IOException e) {
			EventData m = new EventData("Unable to get OutputStream");
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.outputStream = null;
		}
		
		this.done = false;
	}
	
	/**
	 * Destructor. Ensures all IO streams and sockets are closed.
	 */
	public void finalize() throws Throwable {
		try {
			EventManager.getInstance().unregisterListener(this);
			if(this.inputStream != null)
				this.inputStream.close();
			if(this.outputStream != null)
				this.outputStream.close();
			this.socket.close();
			//EventManager.getInstance().sendEvent("log", new EventMessage("connection garbage collection"));
		} finally {
			super.finalize();
		}
	}
	
	/**
	 * Open input stream and read in objects passed through, forwarding them to the event manager.
	 */
	@Override
	public void run() {
		// try building the ObjectInputStream
		try {
			this.inputStream = this.socket.getInputStream();
			
			try {
				this.in = new ObjectInputStream(this.inputStream);
				try {
					while(true) {
						Event event = (Event) this.in.readObject();
						this.eventManager.sendEvent(this, event);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				EventData m = new EventData("Unable to get ObjectInputStream");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.in = null;
			}
			
		} catch (IOException e) {
			EventData m = new EventData("Unable to get InputStream");
			e.printStackTrace();
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.inputStream = null;
		} finally {
			// if connection was still available we would still be in the loop
			// when you're here it means an exception was thrown, and here we are
			
			this.done = true;
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
	
	public boolean send(Event event) {
		if(out != null) {
			try {
				out.writeObject(event);
				out.flush();
				return true;
			} catch (IOException e) {
				EventData m = new EventData("Unable to send event to client");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.done = true;
				
				return false;
			}
		}
		
		this.done = true;
		return false;
	}
	
	/**
	 * Send events from the event manager to the other machine on the connection
	 * @param name Name of the event
	 * @param event Data about the event
	 */
	public boolean processMessage(String name, EventData event) {
		if(out != null) {
			try {
				out.writeObject(name);
				out.flush();
				out.writeObject(event);
				out.flush();
				return true;
			} catch (IOException e) {
				EventData m = new EventData("Unable to send event to client");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.done = true;
				
				return false;
			}
		}

		this.done = true;
		return false;
	}
	
	/**
	 * Check to see if the connection is finished
	 * @return Whether or not the connection is done
	 */
	public boolean isDone() { return this.done; }
	
	private boolean done;
	
	private EventManager eventManager;
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
}

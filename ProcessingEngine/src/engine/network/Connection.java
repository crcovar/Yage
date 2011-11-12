package engine.network;

import java.net.Socket;
import java.io.*;

import com.google.gson.Gson;

import engine.GameObject;
import engine.events.Event;
import engine.events.EventManager;
import engine.events.EventData;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Connection extends GameObject implements Runnable {
	
	public static final short CLIENT = 0;
	public static final short PEER = 1;
	public static final short REMOTE_VIEWER = 2;
	
	/**
	 * Default Constructor. 
	 */
	private Connection() {
		super();
		
		this.eventManager = EventManager.getInstance();
		this.socket = null;
		this.gson = new Gson();
		this.done = false;
	}
	
	/**
	 * Constructor for using <code>Connection</code> from a client. <code>Connection</code> is now both ends of a one-to-one link between machines.
	 * Use the <code>Server</code> class if you need to maintain and track multiple connections
	 * @param host Hostname or IP of the <code>Server</code> object you want to connect to.
	 * @param port Port number the <code>Server</code> will be listening on.
	 */
	public Connection(String host, int port, short type) {
		this();
		
		this.type = type;
		
		try {
			this.socket = new Socket(host,port);
			
			try {
				this.outputStream = this.socket.getOutputStream();
				this.out = new PrintWriter(this.outputStream);
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
		this();
		
		this.socket = socket;
		
		// try building the ObjectOutputStream
		try {
			this.outputStream = this.socket.getOutputStream();
			this.out = new PrintWriter(this.outputStream);
		} catch (IOException e) {
			EventData m = new EventData("Unable to get OutputStream");
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.outputStream = null;
		}
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
			this.in = new BufferedReader(new InputStreamReader(this.inputStream));

			try {
				while(true) {
					String json = this.in.readLine();
					Event event = this.gson.fromJson(json, Event.class);
					if(event.getName().equals("netinit")) {
						GameObject.gameTime = event.getTimestamp();
						this.send(new Event("netresponse", new EventData("connection",this.type)));
					} else if(event.getName().equals("netresponse")) {
						this.type = (short) event.getData().getGuid();
						String msg = "";
						switch(this.type) {
						case CLIENT:
							msg += "Client";
							break;
						case PEER:
							msg += "Peer-2-Peer";
							this.eventManager.sendEvent("exchangeplayers", null);
							break;
						case REMOTE_VIEWER:
							msg += "Remote Viewer";
							break;
						default:
							msg += "unknown connection type";
							break;
						}
						this.eventManager.sendEvent("log", new EventData(msg + " Connection established"));
					} else
						this.eventManager.sendEvent(this, event);
				}
			} catch (Exception e) {
				EventData m = new EventData("Couldn't get class");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
			}
			
		} catch (IOException e) {
			EventData m = new EventData("Unable to get InputStream");
			e.printStackTrace();
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.inputStream = null;
			this.done = true;
		}
		
		this.done = true;
				
	}
	
	public boolean send(Event event) {
		if(this.outputStream != null) {
			String eventJson = this.gson.toJson(event);
			this.out.println(eventJson);
			this.out.flush();
			return true;
		}
		
		this.done = true;
		return false;
	}
	
	/**
	 * Send events from the event manager to the other machine on the connection
	 * @param name Name of the event
	 * @param event Data about the event
	 */
/*	public boolean processMessage(String name, EventData event) {
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
*/	
	/**
	 * Check to see if the connection is finished
	 * @return Whether or not the connection is done
	 */
	public boolean isDone() { return this.done; }
	
	/**
	 * Get what type of connection this machine is
	 * @return
	 */
	public short getType() {
		return this.type;
	}
	
	private boolean done;
	
	private EventManager eventManager;
	
	private short type;
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private PrintWriter out;
	private BufferedReader in;
	
	private Gson gson;
}

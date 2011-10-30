package engine.network;

import java.net.Socket;
import java.io.*;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.EventMessage;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Connection extends GameObject implements Runnable {

	public Connection(Socket socket) {
		this.socket = socket;
		
		// try building the ObjectOutputStream
		try {
			this.outputStream = this.socket.getOutputStream();
			try {
				this.out = new ObjectOutputStream(this.outputStream);
			} catch (IOException e) {
				EventMessage m = new EventMessage("Unable to get ObjectOutputStream");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.out = null;
			}
		} catch (IOException e) {
			EventMessage m = new EventMessage("Unable to get OutputStream");
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.outputStream = null;
		}
		
		
	}
	
	public void finalize() throws Throwable {
		try {
			if(this.inputStream != null)
				this.inputStream.close();
			if(this.outputStream != null)
				this.outputStream.close();
			this.socket.close();
		} finally {
			super.finalize();
		}
	}
	
	@Override
	public void run() {
		// try building the ObjectInputStream
		try {
			this.inputStream = this.socket.getInputStream();
			
			try {
				this.in = new ObjectInputStream(this.inputStream);
				try {
					while(true) {
						String name = (String) this.in.readObject();
						EventMessage event = (EventMessage) this.in.readObject();
						if(name.equals("register")) {
							EventManager.getInstance().registerListener(this,event.getMessage());
							EventManager.getInstance().sendEvent("log", new EventMessage("registered connection with " + event.getMessage() + " event."));
						}
						else
							EventManager.getInstance().sendEvent(name, event);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				EventMessage m = new EventMessage("Unable to get ObjectInputStream");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				this.in = null;
			}
			
		} catch (IOException e) {
			EventMessage m = new EventMessage("Unable to get InputStream");
			e.printStackTrace();
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.inputStream = null;
		}
				
	}
	
	public boolean processMessage(String name, EventMessage event) {
		if(out != null) {
			try {
				out.writeObject(name);
				out.flush();
				out.writeObject(event);
				out.flush();
				return true;
			} catch (IOException e) {
				EventMessage m = new EventMessage("Unable to send event to client");
				EventManager.getInstance().sendEvent("log", m);
				m.setMessage(e.getMessage());
				EventManager.getInstance().sendEvent("log", m);
				return false;
			}
		}
		return false;
	}
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private ObjectOutputStream out;
	private ObjectInputStream in;
}

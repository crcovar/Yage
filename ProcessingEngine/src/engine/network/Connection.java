/**
 * 
 */
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
		try {
			this.inputStream = this.socket.getInputStream();
		} catch (IOException e) {
			EventMessage m = new EventMessage("Unable to get InputStream");
			EventManager.getInstance().sendEvent("log", m);
			m.setMessage(e.getMessage());
			EventManager.getInstance().sendEvent("log", m);
			this.inputStream = null;
		}
		
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
		EventManager.getInstance().sendEvent("log", new EventMessage("connection recieved from client"));
		
		
		
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

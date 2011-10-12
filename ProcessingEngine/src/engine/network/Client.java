package engine.network;

import engine.GameObject;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: connect to Server
 * TODO: process messages from Server
 * TODO: override processMessage() to process registered events
 * TODO: send messages to Server
 * TODO: pass messages from Server to other systems
 * TODO: write javadoc
 */
public class Client extends GameObject {
	public Client() {
		super();
		
		EventManager.getInstance().registerListener("toserver", this);
	}
}

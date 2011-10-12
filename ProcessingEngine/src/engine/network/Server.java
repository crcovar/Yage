package engine.network;

import engine.GameObject;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Accept connections from clients
 * TODO: process messages from clients
 * TODO: send messages to clients
 * TODO: override processMessage() to process registered events
 * TODO: pass messages sent from clients to other systems
 * TODO: write javadoc
 */
public class Server extends GameObject {
	public Server() {
		super();
		
		EventManager.getInstance().registerListener("toclient", this);
	}
}

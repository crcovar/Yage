/**
 * 
 */
package engine.network;

import engine.GameObject;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Server extends GameObject {
	public Server() {
		super();
		
		EventManager.getInstance().registerListener("toclient", this);
	}
}

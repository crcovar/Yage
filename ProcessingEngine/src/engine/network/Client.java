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
public class Client extends GameObject {
	public Client() {
		super();
		
		EventManager.getInstance().registerListener("toserver", this);
	}
}

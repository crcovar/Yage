/**
 * 
 */
package engine.events;

import java.util.HashMap;

import engine.GameObject;

/**
 * Singleton class for managing events
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class EventManager extends GameObject {

	/**
	 * Default Constructor
	 */
	private EventManager() {
		super();
		this.listeners = new HashMap<String,GameObject>();
	}
	
	/**
	 * Initialize the event manager if necessary then return it
	 * @return the only instance of the event manager
	 */
	public static EventManager getInstance() {
		if(instance == null) 
			instance = new EventManager();
		
		return instance;
	}
	
	/**
	 * Adds a <code>GameObject</code> to the EventManager to sit and listen for a specified event. Only one <code>GameObject</code> can
	 * register per event. Calling this method with a event name already registered will result in over writing the previous <code>GameObject</code>
	 * @param name Name of the event to register
	 * @param listener <code>GameObject</code> to register to respond to the event
	 */
	public void registerListener(String name, GameObject listener) {
		this.listeners.put(name, listener);
	}
	
	/**
	 * Fires off an event to the <code>EventManager</code> which will then pass it to the right <code>GameObject</code>
	 * @param name Event you're sending
	 * @param msg event details
	 * @return true if the <code>EventManager</code> successfully passes and processes an event, false if there's no listener
	 */
	public boolean sendEvent(String name, String msg) {
		if(this.listeners.containsKey(name)) {
			return this.listeners.get(name).processMessage(name, msg);
		}
		
		return false;
	}
	
	private HashMap<String,GameObject> listeners;
	
	private static EventManager instance = null;
}

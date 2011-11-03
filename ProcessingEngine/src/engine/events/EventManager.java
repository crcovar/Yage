package engine.events;

import java.util.HashMap;
import java.util.LinkedList;

import engine.GameObject;

/**
 * Singleton class for managing events
 * @author Charles Covar (covar1@gmail.com)
 */
public class EventManager extends GameObject {

	/**
	 * Default Constructor
	 */
	private EventManager() {
		super();
		this.listeners = new HashMap<String,LinkedList<GameObject>>();
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
	 * Adds a <code>GameObject</code> to the EventManager to sit and listen for a specified event.
	 * @param listener <code>GameObject</code> to register to respond to the event
	 * @param name Name of the event to register
	 */
	public void registerListener(GameObject listener, String name) {
		if(this.listeners.containsKey(name)) {
			this.listeners.get(name).add(listener);
		} else {
			this.listeners.put(name, new LinkedList<GameObject>());
			this.listeners.get(name).add(listener);
		}
		
		this.sendEvent("log", new EventMessage("Registered " + listener.getClass().getCanonicalName() + " to \"" + name + "\" event."));
	}
	
	/**
	 * Adds a <code>GameObject</code> to the <code>EventManager</code> to sit and listen for all existing events.
	 * @param listener <code>GameObject</code> to register to respond to events
	 */
	public void registerListener(GameObject listener) {
		for(String s : this.listeners.keySet())
			registerListener(listener, s);
	}
	
	/**
	 * Remove registered <code>GameObject</code> from the specified event
	 * @param listener <code>GameObject</code> to remove
	 * @param name Name of the event to remove from
	 */
	public void unregisterListener(GameObject listener, String name) {
		if(this.listeners.get(name).remove(listener))
			this.sendEvent("log", new EventMessage("Unregistered " + listener.getClass().getCanonicalName() + " from \"" + name + "\" event."));
	}
	
	/**
	 * Remove registered <code>GameObject</code> from all existing events
	 * @param listener <code>GameObject</code> to remove
	 */
	public void unregisterListener(GameObject listener) {
		for(String s : this.listeners.keySet())
			unregisterListener(listener, s);
	}
	
	/**
	 * Fires off an event to the <code>EventManager</code> which will then pass it to the right <code>GameObject</code>
	 * @param name Event you're sending
	 * @param event event details
	 * @return true if the <code>EventManager</code> successfully passes and processes an event, false if there's no listener
	 */
	public boolean sendEvent(String name, EventMessage event) {
		event.setTimestamp(GameObject.gameTime); // set timestamp event gets sent, for networking
		
		if(this.listeners.containsKey(name)) {
			for(GameObject g : this.listeners.get(name)) {
				if(!g.processMessage(name, event))
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	private HashMap<String,LinkedList<GameObject>> listeners;
	
	private static EventManager instance = null;
}

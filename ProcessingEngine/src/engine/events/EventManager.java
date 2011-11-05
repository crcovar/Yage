package engine.events;

import java.util.HashMap;
import java.util.LinkedList;

import engine.GameObject;
import engine.network.Connection;

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
		
		this.localQueue = new LinkedList<Event>();
		this.eventQueues = new HashMap<Connection,LinkedList<Event>>();
		
		this.listeners = new HashMap<String,LinkedList<GameObject>>();
		
		this.eventCalled = false;
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
		
		this.sendEvent("log", new EventData("Registered " + listener.getClass().getCanonicalName() + " to \"" + name + "\" event."));
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
			this.sendEvent("log", new EventData("Unregistered " + listener.getClass().getCanonicalName() + " from \"" + name + "\" event."));
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
	 * Remove <code>Connection</code> from the event queues
	 * @param connection 
	 */
	public void unregisterListener(Connection connection) {
		this.eventQueues.remove(connection);
	}
	
	/**
	 * Fires off an event to the <code>EventManager</code> which will then pass it to the right <code>GameObject</code>
	 * @param name Event you're sending
	 * @param event event details
	 * @return true if the <code>EventManager</code> successfully passes and processes an event, false if there's no listener
	 */
	public boolean sendEvent(String name, EventData event) {
		this.eventCalled = true;
		
		Event e = new Event(name, event);
		this.localQueue.add(e);
		
		for(Connection c : this.eventQueues.keySet()) {
			if(!c.isDone())
				c.send(e);
		}
		
		return true;
	}
	
	/**
	 * push an <code>Event</code> sent from a <code>Connection</code> into the corresponding queue 
	 * @param connection <code>Connection</code> that sent the <code>Event</code> 
	 * @param event <code>Event</code> Object that was sent across the network
	 */
	public void sendEvent(Connection connection, Event event) {
		if(this.eventQueues.containsKey(connection)) {
			this.eventQueues.get(connection).add(event);
		} else {
			this.eventQueues.put(connection, new LinkedList<Event>());
			this.eventQueues.get(connection).add(event);
		}
	}
	
	/**
	 * Process any events that need to be handled
	 */
	public void process() {
		if(!eventCalled)
			this.sendEvent("null",null);
		
		for(Connection c : this.eventQueues.keySet())
			if(c.isDone())
				unregisterListener(c);
		
		long gvt = getGVT();
		
		while (this.localQueue.getFirst().getTimestamp() == gvt) {
			Event e = this.localQueue.pop();
			processEvent(e.getName(),e.getData());
			if(this.localQueue.isEmpty())
				break;
		}
		
		for(LinkedList<Event> queue : this.eventQueues.values()) {
			while (!queue.isEmpty() && queue.getFirst().getTimestamp() == gvt) {
				Event e = queue.pop();
				processEvent(e.getName(),e.getData());
			}
		}
		
		this.eventCalled = false;
	}
	
	private boolean processEvent(String name, EventData data) {
		if(this.listeners.containsKey(name)) {
			for(GameObject g : this.listeners.get(name)) {
				if(!g.processMessage(name, data))
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	private long getGVT() {
		long gvt = 0;
		
		Event localEvent = this.localQueue.getFirst();
		
		gvt = localEvent.getTimestamp();
		
		for(LinkedList<Event> queue : this.eventQueues.values()) {
			if(queue.isEmpty())
				return gvt;
			Event qEvent = queue.getFirst();
			gvt = Math.min(gvt, qEvent.getTimestamp());
		}
		
		return gvt;		
	}
	
	private boolean eventCalled;
	
	private LinkedList<Event> localQueue;
	private HashMap<Connection,LinkedList<Event>> eventQueues;
	private HashMap<String,LinkedList<GameObject>> listeners;
	
	private static EventManager instance = null;
}

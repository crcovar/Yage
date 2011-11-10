package engine;

import engine.events.Event;
import engine.events.EventData;

/**
 * Superclass of the engine
 * @author Charles Covar (covar1@gmail.com)
 */
public abstract class GameObject {
	/**
	 * Default Constructor, creates a GUID for the object and increments the counter
	 */
	public GameObject() {
		this.localOnly = false;
		this.gUId = numGameObjects;
		numGameObjects++;
	}
	
	/**
	 * When object is destroyed decrement the game counter
	 */
	/*public void finalize() throws Throwable {
		try {
			numGameObjects--;
		} finally {
			super.finalize();
		}
	}*/
	
	/**
	 * Set instance variable of game object
	 * @param name name of the instance variable
	 * @param value Value of the parameter to set
	 * @return true if the parameter exists and value was set correctly
	 */
	public boolean setParam(String name, String value) { return false; }
	
	/**
	 * Outputs a string listing all of a <code>GameObject</code>'s parameters in a format readable by the scripting parsers
	 * @return String of all the parameters in the <code>GameObject</code>
	 */
	public String printParams() { return "GUId="+ this.gUId; }
	
	/**
	 * Process an event. Can be overridden in subclasses to account for things like local vs remote events.
	 * @param event <code>Event</code> object to be processed
	 * @return true if the message gets processed successfully
	 */
	public boolean processEvent(Event event) {
		if((localOnly && event.isLocal()) || (!localOnly))
			return processMessage(event.getName(), event.getData());
		else
			return false;
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventData event) { return false; }
	
	/**
	 * Get the Global Unique Identifier of the <code>GameObject</code>
	 * @return Global Unique Identifier that represents the <code>GameObject</code>
	 */
	public int getGUId() { return gUId; }
	
	protected int gUId;
	protected boolean localOnly;
	
	public static long gameTime = Long.MIN_VALUE;
	private static int numGameObjects = 0;
}

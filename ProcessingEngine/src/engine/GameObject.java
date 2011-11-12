package engine;

import engine.events.Event;
import engine.events.EventData;
import engine.events.EventManager;

/**
 * Superclass of the engine
 * @author Charles Covar (covar1@gmail.com)
 */
public abstract class GameObject {
	/**
	 * Default Constructor, creates a GUID for the object and increments the counter
	 */
	public GameObject() {
		this.localEvents = true;
		this.remoteEvents = true;
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
	 * Set the flags for handling local and remote events.
	 * @param local Set to <code>true</code> if you want this <code>GameObject</code> to process local events
	 * @param remote Set to <code>true</code> if you want this <code>GameObejct</code> to process remote events 
	 */
	public void setEventFlags(boolean local, boolean remote) {
		this.localEvents = local;
		this.remoteEvents = remote;
	}
	
	/**
	 * Process an event. Can be overridden in subclasses to account for things like local vs remote events.
	 * @param event <code>Event</code> object to be processed
	 * @return true if the message gets processed successfully
	 */
	public boolean processEvent(Event event) {
		if((event.isLocal() && localEvents) || (!event.isLocal() && remoteEvents)) {
			return processMessage(event.getName(), event.getData());
		} else
			return false;
	}
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param event message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, EventData event) {
		if(name.equals("gamestatechange")) {
			gameState = (short) event.getValue();
		}
		return false;
	}
	
	/**
	 * Get the Global Unique Identifier of the <code>GameObject</code>
	 * @return Global Unique Identifier that represents the <code>GameObject</code>
	 */
	public int getGUId() { return gUId; }
	
	public static short getGameState() {
		return gameState;
	}


	protected int gUId;
	protected boolean localEvents;
	protected boolean remoteEvents;
	
	public static long gameTime = Long.MIN_VALUE;
	private static int numGameObjects = 0;
	
	private static short gameState = 0;
	public static final short GAME_STATE_MENU = 0;
	public static final short GAME_STATE_LEVEL = 1;
	public static final short GAME_STATE_REPLAY = 2;
	public static final short GAME_STATE_END = 3;
}

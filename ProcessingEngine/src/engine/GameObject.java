package engine;

/**
 * Superclass of the engine
 * @author Charles Covar (covar1@gmail.com)
 */
public abstract class GameObject {
	/**
	 * Default Constructor, creates a GUID for the object and increments the counter
	 */
	public GameObject() {
		this.gUId = numGameObjects;
		numGameObjects++;
	}
	
	/**
	 * When object is destroyed decrement the game counter
	 */
	public void finalize() throws Throwable {
		try {
		numGameObjects--;
		} finally {
			super.finalize();
		}
	}
	
	/**
	 * Set instance variable of game object
	 * @param name name of the instance variable
	 * @param value Value of the parameter to set
	 * @return true if the parameter exists and value was set correctly
	 */
	public boolean setParam(String name, String value) { return false; }
	
	/**
	 * Process a message that was sent from the event manager
	 * @param name Type of Event that was sent
	 * @param msg message to process
	 * @return true if the event message was processed successfully
	 */
	public boolean processMessage(String name, String msg) { return false; }
	
	protected int gUId;
	private static int numGameObjects = 0;
}

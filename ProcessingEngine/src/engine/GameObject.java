/**
 * 
 */
package engine;

/**
 * Superclass of the engine
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public abstract class GameObject {
	
	public GameObject() {
		this.gUId = numGameObjects;
		numGameObjects++;
	}
	
	public void finalize() throws Throwable {
		numGameObjects--;
	}
	
	public boolean setParam(String name, String value) { return false; }
	
	public boolean processMessage(String name, String msg) { return false; }
	
	protected int gUId;
	private static int numGameObjects = 0;
}

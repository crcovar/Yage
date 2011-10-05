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
	protected int gUId;
	private static int numGameObjects = 0;
	
	public GameObject() {
		this.gUId = numGameObjects;
		numGameObjects++;
	}
	
	
}

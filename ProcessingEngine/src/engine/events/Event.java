/**
 * 
 */
package engine.events;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Event {
	
	public Event(String name, EventData data) {
		this.name = name;
		this.data = data;
	}
	
	public Event() {
		this(null, null);
	}
	
	public String getName() {
		return this.name;
	}
	
	public EventData getData() {
		return this.data;
	}
	
	private String name;
	private EventData data;

}

/**
 * 
 */
package engine.events;

import java.io.Serializable;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Event implements Serializable {
	
	public Event(String name, EventData data) {
		this.name = name;
		this.data = data;
		
		setTimestamp();
	}
	
	public Event() {
		this(null, null);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setTimestamp() {
		this.timestamp = GameObject.gameTime;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public EventData getData() {
		return this.data;
	}
	
	private String name;
	private long timestamp;
	private EventData data;
	
	private static final long serialVersionUID = -8132000945668948369L;
}

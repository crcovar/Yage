/**
 * 
 */
package engine.events;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Event {
	
	public Event(String name, EventData data) {
		this.name = name;
		this.data = data;
		this.local = true;
		
		setTimestamp();
	}
	
	public Event() {
		this(null, null);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public void makeRemote() {
		this.local = false;
	}
	
	public boolean isLocal() {
		return this.local;
	}
	
	private String name;
	private long timestamp;
	private EventData data;
	private boolean local;
}

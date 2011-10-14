package engine.events;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Event {
	public Event() {
		this.msg = "";
		this.object = null;
	}
	
	public Event(String message) {
		this();
		this.msg = message;
	}
	
	public Event(GameObject object) {
		this();
		this.object = object;
	}
	
	public Event(String message, GameObject object) {
		this.msg = message;
		this.object = object;
	}
	
	public void setMessage(String message) {
		this.msg = message;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	public void setObject(GameObject object) {
		this.object = object;
	}
	
	public GameObject getObject() {
		return this.object;
	}
	
	private String msg;
	private GameObject object;
}

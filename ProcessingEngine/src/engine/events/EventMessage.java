package engine.events;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class EventMessage {
	public EventMessage() {
		this.msg = "";
		this.object = null;
	}
	
	public EventMessage(String message) {
		this();
		this.msg = message;
	}
	
	public EventMessage(GameObject object) {
		this();
		this.object = object;
	}
	
	public EventMessage(String message, GameObject object) {
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

package engine.events;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class EventData {
	
	public EventData() {
		this.msg = "";
		this.object = null;
	}
	
	public EventData(String message) {
		this();
		this.msg = message;
	}
	
	public EventData(GameObject object) {
		this();
		this.object = object;
	}
	
	public EventData(String message, GameObject object) {
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

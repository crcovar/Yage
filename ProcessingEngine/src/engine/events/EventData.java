package engine.events;

import java.io.Serializable;

import engine.GameObject;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class EventData implements Serializable {
	
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
	
	public void setTimestamp(long gameTime) {
		this.timestamp = gameTime;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}

	private static final long serialVersionUID = 8645183011653698049L;
	
	private String msg;
	private GameObject object;
	private long timestamp;
}

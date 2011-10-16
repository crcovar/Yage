package engine.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import engine.GameObject;
import engine.events.EventManager;
import engine.events.RenderEvent;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Support half speed
 * TODO: Support 2x speed
 */
public class Replay extends GameObject {
	/**
	 * Default Constructor
	 */
	public Replay(String file) {
		super();
		
		this.done = false;
		this.speed = Replay.NORMAL;
		this.reader = null;
		this.in = null;
		
		try {
			reader = new FileReader(file);
			in = new BufferedReader(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.objects = new HashMap<Integer, RenderEvent>();
	}
	
	public void finalize() throws Throwable {
		try {
			reader.close();
			in.close();
		} finally {
			super.finalize();
		}
	}
	
	/**
	 * Are we done with the replay?
	 * @return true if there is no more replay to play
	 */
	public boolean isDone() { return this.done; }
	
	/**
	 * Toggle the speed to play replays at
	 */
	public void toggleSpeed() {
		if(this.speed == Replay.DOUBLE)
			this.speed = Replay.HALF;
		else
			this.speed++;
	}
	
	/**
	 * Get the speed play replays at
	 * @return Either half speed, normal, or double
	 */
	public int getSpeed() {
		return this.speed;
	}

	public void update() {
		try {
			String line = in.readLine();
			if(line != null) {

				//do parsing				
				String[] lineArray = line.split(" ");
				String[] parameterArray = lineArray[1].split("=");
				
				int key = -1;
				
				if(parameterArray[0].equals("GUId"))
					key = Integer.parseInt(parameterArray[1]);

				// now check if there's already an object at the key
				// if there's not add it to the HashMap
				if(!this.objects.containsKey(key)) {
					RenderEvent r = new RenderEvent();
					r.setMessage(lineArray[0]);
					
					this.objects.put(key, r);
				}
				
				// we have something at the key so just set the parameters
				if(lineArray.length > 2) {
					for(int i=2;i<lineArray.length;i++) {
						parameterArray = lineArray[i].split("=");
						if(parameterArray[0].equals("draw")) {
							if(parameterArray[1].equals("false"))
								this.objects.remove(key);
						} else
							this.objects.get(key).setParam(parameterArray[0], parameterArray[1]);
					}
				}
			} else {
				this.done = true;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw() {
		switch (this.speed) {
		case Replay.HALF:
		case Replay.NORMAL:
		case Replay.DOUBLE:
			for(RenderEvent obj : this.objects.values()) {
				EventManager.getInstance().sendEvent("draw", obj);
			}
			break;
		default:break;
		}
	}
	
	private boolean done;
	private int speed;
	
	public static final int NORMAL = 1;
	public static final int HALF = 0;
	public static final int DOUBLE = 2;
	
	private FileReader reader;
	private BufferedReader in;

	private HashMap<Integer,RenderEvent> objects;

}

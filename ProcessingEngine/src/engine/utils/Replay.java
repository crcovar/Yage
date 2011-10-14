/**
 * 
 */
package engine.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import engine.GameObject;
import engine.tileobject.DeathZone;
import engine.tileobject.Platform;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.tileobject.VictoryZone;
import engine.character.Character;
import engine.character.Player;

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
		
		this.objects = new HashMap<Integer, GameObject>();
		this.objectsBackBuffer = null;
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
				this.objectsBackBuffer = (HashMap<Integer, GameObject>) this.objects.clone();
				
				//do parsing				
				String[] lineArray = line.split(" ");
				String[] parameterArray = lineArray[1].split("=");
				
				int key = -1;
				
				if(parameterArray[0].equals("GUId"))
					key = Integer.parseInt(parameterArray[1]);

				// now check if there's already an object at the key
				if(!this.objects.containsKey(key)) {
					GameObject t = null;
					String obj = lineArray[0].toLowerCase();
					if(obj.equals("platform"))
						t = new Platform();
					else if(obj.equals("deathzone"))
						t = new DeathZone();
					else if(obj.equals("victoryzone"))
						t = new VictoryZone();
					else if(obj.equals("spawnpoint"))
						t = new SpawnPoint();
					else if (obj.equals("player"))
						t = new Player();
					
					this.objects.put(key, t);
				}
				
				// we have something at the key so just set the parameters
				if(lineArray.length > 2) {
					for(int i=2;i<lineArray.length;i++) {
						parameterArray = lineArray[i].split("=");
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
			for(GameObject obj : this.objects.values()) {
				if(obj instanceof Character) {
					((Character) obj).draw();
				} else if(obj instanceof TileObject) {
					((TileObject) obj).draw();
				}
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
	
	private HashMap<Integer,GameObject> objectsBackBuffer;
	private HashMap<Integer,GameObject> objects;

}

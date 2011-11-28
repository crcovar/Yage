package engine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import engine.GameObject;
import engine.events.EventData;
import engine.events.EventManager;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class ConfigManager extends GameObject {
	
	private ConfigManager()
	{
		super();
		
		this.options = new HashMap<String,String>();
		
		try {
			FileReader reader = new FileReader("test.config");
			BufferedReader in = new BufferedReader(reader);
			
			String line;
			while((line = in.readLine()) != null) {
				if(line.startsWith("#") || line.equals(""))
					continue;
				
				String[] lineArray = line.split("=");
				this.options.put(lineArray[0], lineArray[1]);
			}
		} catch(IOException e) {
			EventManager.getInstance().sendEvent("log", new EventData(e.getLocalizedMessage()));
		}
	}
	
	public static ConfigManager getInstance()
	{
		if(instance == null)
			instance = new ConfigManager();
		return instance;
	}
	
	public String getOption(String key) {
		return this.options.get(key);
	}
	
	private static ConfigManager instance = null;
	private HashMap<String,String> options;

}
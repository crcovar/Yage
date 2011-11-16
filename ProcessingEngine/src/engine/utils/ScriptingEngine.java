package engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class ScriptingEngine {

	/**
	 * Default Constructor
	 */
	private ScriptingEngine() {
		this.manager = new ScriptEngineManager();
		this.jsEngine = this.manager.getEngineByMimeType("text/javascript");
		
		File scriptsDir = new File("scripts");
		parseScriptsDir(scriptsDir);
		
		this.invocableEngine = (Invocable) this.jsEngine;
	}
	
	/**
	 * Fetch the instance of this Singleton class
	 * @return
	 */
	public static ScriptingEngine getInstance() {
		if(instance == null)
			instance = new ScriptingEngine();
		return instance;
	}
	
	/**
	 * Recursive method for going through a directory tree and loading any javascript files into the ScriptEngine
	 * @param dir
	 */
	private void parseScriptsDir(File dir) {
		if(dir.isDirectory()) {
			for(File file : dir.listFiles()) {
				if(file.isFile())
					this.evalScriptFile(file.getAbsolutePath());
				else if(file.isDirectory())
					this.parseScriptsDir(file);
			}
		}
	}
	
	public void evalScriptFile(String filename) {
		try {
			FileReader reader = new FileReader(filename);
			
			try {
				this.jsEngine.eval(reader);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Evaluates a <code>String</code> containing JavaScript
	 * @param script Body of the script
	 */
	public void evalScriptText(String script) {
		try {
			this.jsEngine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public Object invokeFunction(String name, Object... args) {
		try {
			return this.invocableEngine.invokeFunction(name, args);
		} catch (NoSuchMethodException e) {
			return e.getStackTrace();
		} catch (ScriptException e) {
			return e.getStackTrace();
		}
	}

	public Object invokeMethod(Object thiz, String name, Object... args) {
		try {
			return this.invocableEngine.invokeMethod(thiz, name, args);
		} catch (NoSuchMethodException e) {
			return e.getStackTrace();
		} catch (ScriptException e) {
			return e.getStackTrace();
		}
	}
	private ScriptEngineManager manager;
	private ScriptEngine jsEngine;
	private Invocable invocableEngine;
	
	private static ScriptingEngine instance = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScriptingEngine.getInstance().invokeFunction("hello");
	}
}

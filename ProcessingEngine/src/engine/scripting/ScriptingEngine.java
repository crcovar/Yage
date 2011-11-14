package engine.scripting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.*;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class ScriptingEngine implements Invocable {

	private ScriptingEngine() {
		this.manager = new ScriptEngineManager();
		this.jsEngine = this.manager.getEngineByMimeType("text/javascript");
	}
	
	public static ScriptingEngine getInstance() {
		if(instance == null)
			instance = new ScriptingEngine();
		return instance;
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
	
	public void evalScriptText(String script) {
		try {
			this.jsEngine.eval(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> T getInterface(Class<T> clasz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getInterface(Object thiz, Class<T> clasz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invokeFunction(String name, Object... args)
			throws ScriptException, NoSuchMethodException {
		Invocable ie = (Invocable)this.jsEngine;
		return ie.invokeFunction(name, args);
	}

	@Override
	public Object invokeMethod(Object thiz, String name, Object... args)
			throws ScriptException, NoSuchMethodException {
		Invocable ie = (Invocable)this.jsEngine;
		return ie.invokeMethod(thiz, name, args);
	}
	private ScriptEngineManager manager;
	private ScriptEngine jsEngine;
	
	private static ScriptingEngine instance = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "print('hello, world')";
		ScriptingEngine.getInstance().evalScriptText(s);
		ScriptingEngine.getInstance().evalScriptFile("scripts/test.js");
		try {
			ScriptingEngine.getInstance().invokeFunction("hello");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

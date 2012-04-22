package engine.utils;

/**
 * @author Charles Covar
 *
 */
public class Camera extends engine.GameObject {

	public Camera()
	{
		ConfigManager config = ConfigManager.getInstance();
		this.width = Integer.parseInt(config.getOption("width"));
		this.height = Integer.parseInt(config.getOption("height"));
	}
	
	private int width;
	private int height;
}

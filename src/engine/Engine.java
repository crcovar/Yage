package engine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import engine.utils.ConfigManager;

public class Engine extends StateBasedGame
{
    public static final int GAME_STATE_MENU = 0;
	public static final int GAME_STATE_LEVEL = 1;
	public static final int GAME_STATE_REPLAY = 2;
	public static final int GAME_STATE_WIN = 3;
	public static final int GAME_STATE_LOSE = 4;
    
    public Engine() {
        super("Yage");
        
        this.gameState = GAME_STATE_MENU;

    }
    
    public int getGameState() { return this.gameState; }
    
    @Override
    public void postRenderState(GameContainer gc, Graphics g) throws SlickException {
        g.drawString("Hello World", 100, 100);
    }
    
    public static void main(String[] args) throws SlickException {
    	ConfigManager cm = ConfigManager.getInstance();
        
        String w = cm.getOption("width");
        String h = cm.getOption("height");
        String f = cm.getOption("fullscreen");
    	
		int width = 640;
		int height = 480;
		boolean fullscreen = false;
		
		if(w != null)
			width = Integer.parseInt(w);
		if(h != null)
			height = Integer.parseInt(h);
		if(f != null)
			fullscreen = Boolean.parseBoolean(f);
        
        AppGameContainer app = new AppGameContainer(new Engine());
        app.setDisplayMode(width, height, fullscreen);
        app.start();
    }
    
    private static short gameState;

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}
}
import org.newdawn.slick.*;

import engine.utils.ConfigManager;

public class Engine extends BasicGame
{
    public static final short GAME_STATE_MENU = 0;
	public static final short GAME_STATE_LEVEL = 1;
	public static final short GAME_STATE_REPLAY = 2;
	public static final short GAME_STATE_WIN = 3;
	public static final short GAME_STATE_LOSE = 4;
    
    public Engine() {
        super("Yage");
        
        this.gameState = GAME_STATE_MENU;
        

    }
    
    public short getGameState() { return this.gameState; }
    
    @Override
    public void init(GameContainer gc) throws SlickException {
        ConfigManager cm = ConfigManager.getInstance();
           
        String w = cm.getOption("width");
        String h = cm.getOption("height");
    	
		int width = 640;
		int height = 480;
		
		if(w != null)
			width = Integer.parseInt(w);
		if(h != null)
			height = Integer.parseInt(h);
        
        gc.setDisplayMode(width, height, false);
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.drawString("Hello World", 100, 100);
    }
    
    public static void main(String[] args) throws SlickException {
        
        AppGameContainer app = new AppGameContainer(new Engine());
        app.start();
    }
    
    private static short gameState;
}
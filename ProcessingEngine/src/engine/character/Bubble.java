package engine.character;

import engine.events.EventData;
import engine.events.EventManager;
import engine.tileobject.SpawnPoint;
import engine.tileobject.TileObject;
import engine.utils.BubbleState;

/**
 * @author Charles Covar (covar1@gmail.com)
 *
 */
public class Bubble implements Character {
	
	/**
	 * Default Constructor
	 */
	public Bubble() {
		this.x = 0;
		this.y = 0;
		this.color = BubbleState.RED;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpawn(SpawnPoint s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gravity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTopBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSmallTopBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBottomBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSmallBottomBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLeftBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSmallLeftBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRightBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSmallRightBound() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void collideLeft(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collideRight(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collideTop(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collideBottom(int bound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		EventManager.getInstance().sendEvent("draw", new EventData("Bubble",x,y));
	}
	
	private Bubble topLeft,
				   topRight,
				   left,
				   right,
				   bottomLeft,
				   bottomRight;
	
	private int x, y;
	private BubbleState color;
}

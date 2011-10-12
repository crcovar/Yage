/**
 * 
 */
package engine.character;

import engine.tileobject.SpawnPoint;

/**
 * @author Charles Covar (covar1@gmail.com)
 * TODO: Write javadoc
 */
public interface Character {
	
	public void setSpawn(SpawnPoint s);
	public void death();
	
	public void moveLeft();
	public void moveRight();
	public void moveUp();
	public void moveDown();
	
	public void gravity();
	
	public int getTopBound();
	public int getSmallTopBound();
	public int getBottomBound();
	public int getSmallBottomBound();
	public int getLeftBound();
	public int getSmallLeftBound();
	public int getRightBound();
	public int getSmallRightBound();
	
	public void collideLeft(int bound);
	public void collideRight(int bound);
	public void collideTop(int bound);
	public void collideBottom(int bound);
	
	public void update();
	public void draw();

}

package engine.sprites;

public class Frame implements Comparable {
    public Frame(int id, int sX, int sY, int eX, int eY) {
        this.id = Math.abs(id);
        this.startX = sX;
        this.startY = sY;
        this.endX = eX;
        this.endY = eY;
    }
    
    public int getID() { return this.id; }
    
    public int compareTo(Frame f) {
        return f.getID() - this.getID();
    }
    
    private int id;
    
    private int startX;
    private int endX;
    private int startY;
    private int endY;
}
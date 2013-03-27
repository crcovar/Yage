package engine.sprites;

import java.util.*;

import org.newdawn.slick.*;

public class Sprite {
    public Sprite(String name, String src, int numFrames) {
        this.name = name;
        this.source = src;
        this.numFrames = numFrames;
        
        this.frames = new ArrayList<Frame>();
        this.animations = new HashMap<String, Animation>();
    }
    
    @SuppressWarnings("unchecked")
	public void addFrame(Frame f) {
        this.frames.add(f);
        
        if (this.frames.size() == this.numFrames) {
            Collections.sort(this.frames);
        }
    }
    
    public void addAnimation(Animation a) {
        this.animations.put(a.name, a);
    }
    
    private String name;
    private String source;
    private int numFrames;
    
    private ArrayList<Frame> frames;
    private HashMap<String, Animation> animations;
}
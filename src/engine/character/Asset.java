package engine.character;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import processing.core.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import engine.GameObject;

public class Asset extends GameObject {
	public Asset(String id, PApplet parent) {
		this.parent = parent;
		
		PImage image = this.parent.loadImage("assets/"+id+"/"+id+".png");
		image.loadPixels();
		
		try {
			FileReader reader = new FileReader("assets/"+id+"/"+id+".json");
			BufferedReader in = new BufferedReader(reader);
			StringBuilder sb = new StringBuilder();
			
			String s = "";
			while ((s = in.readLine()) != null) {
				sb.append(s);
			}
			
			Type hashMapType = new TypeToken<HashMap<String, int[]>>() {}.getType();
			this.info = (new Gson()).fromJson(sb.toString(), hashMapType);
			
			// now that we have all the info on the asset, load the frames
			
			this.frames = new PImage[this.info.get("x").length];
			for(int i=0; i < this.frames.length; i++) {
				this.frames[i] = this.parent.createImage(this.info.get("w")[i], this.info.get("h")[i], PConstants.RGB);
				this.frames[i].loadPixels();
				for(int x=0; x < this.frames[i].width; x++) {
					for(int y=0; y < this.frames[i].height; y++) {
						int srcX = this.info.get("x")[i] + x;
						int srcY = this.info.get("y")[i] + y;
						
						this.frames[i].pixels[x+y * this.frames[i].width] = image.pixels[srcX + srcY * image.width]; 
					}
				}
				
				this.frames[i].updatePixels();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private PApplet parent;
	private PImage[] frames;
	private HashMap<String, int[]> info;
}

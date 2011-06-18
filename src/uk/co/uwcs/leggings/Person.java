package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import sun.applet.Main;

public class Person extends Lego{
	private PApplet parent;
	private float x,y,height,width;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();

	public Person(PApplet p, float x, float y) {
		this.x = x;
		this.y = y;
		this.height = 4;
		this.width = 2;
		parent = p;
	}
	public void update(Brick[][] collisionMap) {
		y += 10/parent.frameRate;
		
		/*if(x*HEIGHT+height <= ) {
			
		}*/
	}

	public void draw() {
		parent.image(images.get("default"), x, y);
	}
	
	
}

package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Person {
	private PApplet parent;
	private float x,y;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();

	public Person(PApplet p, float x, float y) {
		this.x = x;
		this.y = y;
		parent = p;
	}
	public void update() {
		y = y + 10/parent.frameRate;
	}

	public void draw() {
		parent.image(images.get("default"), x, y);
	}
	
	
}

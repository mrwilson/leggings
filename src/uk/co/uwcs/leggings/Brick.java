package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Brick extends Lego {
	private PApplet parent;
	private int x,y,height,width;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();

	public Brick(PApplet p, int x, int y) {
		parent = p;
		this.x = x;
		this.y = y;
		this.height = 10;
		this.width = 200;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}

	public void draw() {
		parent.rect(x*WIDTH,y*HEIGHT,width*WIDTH,height*HEIGHT);
	}
}

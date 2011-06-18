package uk.co.uwcs.leggings;

import processing.core.PApplet;

public class Brick extends Lego {
	private int x,y,height,width;
	private PApplet parent;
	public Brick(PApplet p, int x, int y) {
		parent = p;
		this.x = x;
		this.y = y;
		this.height = 10;
		this.width = 20;
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

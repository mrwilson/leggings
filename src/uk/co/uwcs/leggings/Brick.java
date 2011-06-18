package uk.co.uwcs.leggings;

import processing.core.PApplet;

public class Brick extends Lego {
	private PApplet parent;
	private int x,y,height,width;
	
	public Brick(PApplet p, int x, int y, int height, int width) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
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
		
	}
}

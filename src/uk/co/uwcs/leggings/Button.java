package uk.co.uwcs.leggings;

import processing.core.PApplet;
import processing.core.PImage;


public class Button {
	private PApplet parent;
	private PImage buttonImage;
	private int x;
	private int y;
	private int width;
	private int height;
	private int flag;
	
	public Button(PApplet p, String path, int x, int y, int width, int height, int flag) {
		this.parent = p;
		this.buttonImage = parent.loadImage(path);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.flag = flag;
	}
	
	public boolean isClicked(int x, int y) {
		
		if (this.x <= x && x <= this.x + width) {
			if (this.y <= y && y <= this.y + height) {
				return true;
			}
		}
		return false;
	}
	
	public int getFlag(){
		return flag;
	}
	
	public PImage getButtonImage() {
		return buttonImage;
	}
	
	public void draw() {
		parent.image(buttonImage, x, y);
	}
	
}

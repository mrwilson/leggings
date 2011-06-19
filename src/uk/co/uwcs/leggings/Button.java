package uk.co.uwcs.leggings;

import processing.core.PApplet;
import processing.core.PImage;


public class Button {
	private PApplet parent;
	private PImage buttonImage1;
	private PImage buttonImage2;
	private PImage frontimage;
	private int x;
	private int y;
	private int width;
	private int height;
	private int flag;
	
	public Button(PApplet p, String path, int x, int y, int width, int height, int flag) {
		this.parent = p;
		this.buttonImage1 = parent.loadImage("../res/images/pressedbutton.png");
		this.buttonImage2 = parent.loadImage("../res/images/greybutton.png");
		this.frontimage = parent.loadImage(path);
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
		return buttonImage1;
	}
	
	public void draw(int on) {
		if (on==1)
			parent.image(buttonImage1, x, y,width,height );
		else
			parent.image(buttonImage2, x, y,width,height );
		parent.image(frontimage, x+12, y,24,64);
		
	}

	public void draw() {
	//	parent.image(buttonImage1, x, y,width,height);		
		parent.image(frontimage, x, y);
	}
}

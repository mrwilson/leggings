package uk.co.uwcs.leggings;

import processing.core.PApplet;
import processing.core.PImage;

public class SplashScreen extends Screen {
	PApplet parent;

	public SplashScreen(PApplet parent) {
		this.parent = parent;
	}

	public void display() {
		parent.background(0);

		PImage bg = parent.loadImage("../res/images/leggings.png");
		PImage start = parent.loadImage("../res/images/start1.png");
		//PImage logo = parent.loadImage("http://uwcs.co.uk/static/img/logo.png", "png");
		PImage exit = parent.loadImage("../res/images/exit1.png");
		
		parent.image(bg, 0, 0, parent.width, parent.height);
		//parent.image(logo, 0, 0);
		parent.image(start, 10, 400);
		parent.image(exit, 375, 400);
		
	}

	public void update() {
	}
	
	public int mousePressed(int mouseX, int mouseY) {
		if (10 <= mouseX && 
			mouseX <= 376 &&
			400 <= mouseY &&
			mouseY <= 516) {
			return 1;
			
		}
		if (375 <= mouseX && 
				mouseX <= 736 &&
				400 <= mouseY &&
				mouseY <= 516) {
			System.exit(0);
		}
		return 2;
	}
	
}

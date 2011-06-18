package uk.co.uwcs.leggings;

import processing.core.PApplet;
import processing.core.PImage;

public class SplashScreen extends Screen {
	PApplet parent;

	public SplashScreen(PApplet parent) {
		this.parent = parent;
	}

	public void display() {
		PImage logo = parent.loadImage("http://uwcs.co.uk/static/img/logo.png", "png");
		parent.image(logo, 0, 0);
		parent.text("Leggings: A game about things.", 400, 300);
	}

	public void update() {
	}
}

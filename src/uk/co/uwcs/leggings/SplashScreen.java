package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PImage;

public class SplashScreen extends Screen {
	Main parent;
	ArrayList<Button> buttons;
	PImage bg;

	public SplashScreen(Main parent) {
		this.parent = parent;
		buttons = new ArrayList<Button>();
		buttons.add(new Button(parent, "../res/images/start1.png", 10, 400, 361, 106, 1));
		buttons.add(new Button(parent, "../res/images/exit1.png", 375, 400, 361, 106, 0));
		bg = parent.loadImage("../res/images/leggings.png");
	}

	public void display() {
		parent.background(0);

		parent.image(bg, 0, 0, parent.width, parent.height);
		Iterator<Button> it = buttons.iterator();
		while(it.hasNext()) {
			it.next().draw();
		}

		
	}

	public void update() {

		
	}
	
	public int mousePressed(int mouseX, int mouseY) {
		Iterator<Button> it = buttons.iterator();
		while( it.hasNext()) {
			Button temp = it.next();
			if (temp.isClicked(mouseX, mouseY)) {
				switch(temp.getFlag()) {
				case 1 : parent.changeScreen(new World(parent, "../res/oep/level6.oel"));break; 
				case 0 : System.exit(0); break;
				default : break;
				
				}
			}
		}
	return 0;
	}
	
}

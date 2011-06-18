package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Person extends Lego{
	private PApplet parent;
	private float x,y;
	private int height,width;
	private int facing;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();

	public Person(PApplet p, float x, float y) {
		facing = 1;
		parent = p;
		this.x = x;
		this.y = y;
		this.width = 1;
		this.height = 4;
	}
	public void update(Brick[][] collisionMap) {
		
		boolean[] forwards = new boolean[height];
		boolean[] downwards = new boolean[width];
		boolean down = false;
		boolean ahead = false;

		for(int j=0; j<width; ++j) {
			int bottom = (int) (y/HEIGHT + height);
			if(collisionMap[(int) (x/WIDTH)+j][bottom] != null) {
				downwards[j] = true;
				down = true;
			}
		}

		for(int i=0; i<height; ++i) {
			int front;
			if(facing == 1) front = (int) (x/WIDTH + width);
			else front = (int) (x/WIDTH + width)-1;
			if(collisionMap[front][(int) (y/HEIGHT)+i] != null) {
				forwards[i] = true;
				ahead = true;
			}
		}
		if(!down) {
			y += 40/parent.frameRate;
		} else {
			if(ahead)facing*=-1;
			x += facing*30/parent.frameRate;
		}
		
	}

	public void draw() {
		parent.pushMatrix(); 
		parent.scale(facing,1);
		parent.image(images.get("default"),facing*x,y+2,facing*width*WIDTH,height*HEIGHT);
		parent.popMatrix();
	}
	
	
}

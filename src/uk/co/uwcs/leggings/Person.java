package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Person extends Lego{
	private PApplet parent;
	private float x,y;
	private int height,width;
	private boolean facing;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();

	public Person(PApplet p, float x, float y) {
		facing = true;
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

		for(int j=0; j<width; ++j) {
			int bottom = (int) (y/HEIGHT + height);
			if(collisionMap[(int) (x/WIDTH)+j][bottom] != null) {
				downwards[j] = true;
				down = true;
				System.out.println("j: " + j);
			}
		}

		for(int i=0; i<height; ++i) {
			int front = (int) (x/WIDTH + width);
			if(collisionMap[front][(int) (y/HEIGHT)+i] != null) {
				forwards[i] = true;
				System.out.println("i: " + i);
			}
		}
		if(!down) {
			y += 10/parent.frameRate;
		}
		
	}

	public void draw() {
		//parent.image(images.get("default"), x, y);
		parent.rect(x,y,width*WIDTH,height*HEIGHT);
	}
	
	
}

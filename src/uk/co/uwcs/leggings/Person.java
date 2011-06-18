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
		y += 10/parent.frameRate;
		/*
		boolean[] forwards = new boolean[height];
		boolean[] downwards = new boolean[width];

		for(int j=0; j<width; ++j) {
			int bottom = (int) (y*HEIGHT + height);
			if(collisionMap[(int) (x*WIDTH)+j][bottom] != null) {
				downwards
			}
		}

		for(int i=0; i<height; ++i) {
		}

		*/
	}

	public void draw() {
		parent.image(images.get("default"), x, y);
	}
	
	
}

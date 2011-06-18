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

	private Timer animation = new Timer((float)0.1);
	private int walkcycle =0;
	private String type;

	public Person(PApplet p, float x, float y) {
		this(p, x, y, "default");
	}
	
	public Person(PApplet p, float x, float y, String type) {
		facing = 1;
		parent = p;
		this.x = x*HEIGHT;
		this.y = y*WIDTH;
		this.width = 1;
		this.height = 4;
		this.type = type;
	}
	public int update(Brick[][] collisionMap) {
		animation.update(1/parent.frameRate);
		if (animation.isOver()){
			walkcycle++;
			if (walkcycle == 8){
				walkcycle = 0;
			}
			animation.reset();
		}
		boolean[] forwards = new boolean[height+1];
		boolean[] downwards = new boolean[width+1];
		boolean down = false;
		boolean step = false;
		boolean ahead = false;

		for(int j=0; j<=width; ++j) {
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
				if(i==height-1)step = true;
				else ahead = true;
			}
		}
		if(type == "climber") {
			if(ahead || step) y -= 30/parent.frameRate;
			else if(!down) y += 40/parent.frameRate;
			else x += facing*30/parent.frameRate;
		} else {
			if(!down) {
				y += 40/parent.frameRate;
			} else {
				if(ahead) {
					facing*=-1;
				}
				else if(step)y-=HEIGHT;
				x += facing*30/parent.frameRate;
			}
		}

		if(y>=parent.height) {
			return 1;
		}
		return 0;

	}

	public void draw() {
		parent.pushMatrix(); 
		parent.scale(facing,1);
		PImage sprite =images.get("sprite").get(walkcycle*54, 0, 54, 128); 
		parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);
		parent.popMatrix();
	}
	
	
}

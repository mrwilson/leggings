package uk.co.uwcs.leggings;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;


public class Brick extends Lego implements Comparable<Brick> {
	private PApplet parent;
	private int x,y,height,width;
	public boolean collidable;
	static HashMap<String,PImage> images = new HashMap<String, PImage>();
	private String tex;
	private String type;

	public Brick(PApplet p, int x, int y, int width, int height, String tex, boolean collidable) {
		this(p,x,y,width,height,tex,collidable,"default");
	}
	
	public Brick(PApplet p, int x, int y, int width, int height, String tex, boolean collidable, String type) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.tex = tex;
		this.collidable = collidable;
		this.type = type;
	}


    public int compareTo(Brick n) {
    	if (n.getY()<y)
    		return 1;
    	if (n.getY()>y)
    		return -1;
    	else return 0;
    }
	
	public Brick(PApplet p, int x, int y, String tex) {
		this(p,x,y,2,1,tex,true);
	}

	public Brick(PApplet p, int x, int y) {
		this(p, x, y, "green");
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
	
	public String getType() {
		return type;
	}

	public void draw() {
		parent.image(images.get(tex),x*WIDTH,y*HEIGHT,width*WIDTH,(int)(height*HEIGHT*1.2));
		//parent.rect(x*WIDTH,y*HEIGHT,width*WIDTH,height*HEIGHT);
	}
}

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
	private PImage sprite;

	private Timer animation = new Timer((float)0.1);
	private Timer anibuild = new Timer((float)0.3);

	private int walkcycle =0;
	private String type;
	
	Boolean climbing = false;
	
	public Person(PApplet p, float x, float y) {
		this(p, x, y, "default");
	}
	
	public Person(PApplet p, float x, float y, String type) {
		facing = 1;
		parent = p;
		this.x = x*WIDTH;
		this.y = y*HEIGHT;
		this.width = 1;
		this.height = 4;
		this.type = type;
	}
	public int update(Brick[][] collisionMap) {
		
		/******************************animation stuff*************************************/
		if (type.equals("walking")||type.equals("climber")){
			animation.update(1/parent.frameRate);
			if (animation.isOver()){
				walkcycle++;
				if (walkcycle == 8){
					walkcycle = 0;
				}
				animation.reset();
			}	
		}else if(type.equals("building")){
			anibuild.update(1/parent.frameRate);
			if (anibuild.isOver()){
				walkcycle++;
				if (walkcycle == 4){
					walkcycle = 0;
				}
				anibuild.reset();
			}				
		}

		/******************************collision stuff************************************/
		boolean[] forwards = new boolean[height+1];
		boolean[] downwards = new boolean[width+1];
		boolean down = false;
		boolean step = false;
		boolean ledge = false;
		boolean ahead = false;

		for(int j=0; j<=width; ++j) {
			int bottom = (int) (y/HEIGHT + height);
			if(collisionMap[(int) Math.floor(x/WIDTH)+j][bottom] != null) {
				downwards[j] = true;
				down = true;
				if (collisionMap[(int) (x/WIDTH)+j][bottom].getType() == "lava") {
					return 3;
				}
			}
		}
		
		int front;
		if(facing == 1) front = (int) (x/WIDTH + width);
		else front = (int) (x/WIDTH + width)-1;
		
		for(int i=0; i<=height; ++i) {
			if(collisionMap[front][(int) (y/HEIGHT)+i] != null) {
				forwards[i] = true;
//				System.out.println(i+"is coliding");
				if(i==(height-1))step = true;
				else if(i==height)ledge = true;
				else ahead = true;
			}
		}
		if(type.equals("climber")) {
//			System.out.println("ahead: " + ahead + "; step: " + step + "; ledge: " + ledge);
			if(ahead){
				System.out.println("1");
				y -= 30/parent.frameRate;
				climbing=true;
			}else if (step && climbing){
				y -= 30/parent.frameRate;		
			} else if (ledge && climbing ){
				System.out.println("2");
				y -= 30/parent.frameRate;
			}else if(!down) {
				System.out.println("3");
				y += 40/parent.frameRate;
			}else if (step){
				y-=HEIGHT;
			}else {
				climbing = false;
				System.out.println("4");
				x += facing*30/parent.frameRate;
			}
		} else {
			if(!down) {
				y += 40/parent.frameRate;
			} else {
				if(ahead)facing*=-1;
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
		if (type.equals("walking")){
			sprite =images.get("sprite").get(walkcycle*54, 0, 54, 128);
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);

		}else if (type.equals("climber")){
			sprite =images.get("sprite").get(walkcycle*54, 0, 54, 128);
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);

		}else if (type.equals("building")){
			sprite =images.get("building").get(walkcycle*105, 0, 105, 128);	
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);
		}
		parent.popMatrix();
	
	
	
	}
}

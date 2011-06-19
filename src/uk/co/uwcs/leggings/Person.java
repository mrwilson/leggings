package uk.co.uwcs.leggings;

import java.util.ArrayList;
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
	private int tobuild = 0; 
	private Timer animation = new Timer((float)0.1);
	private Timer anibuild = new Timer((float)0.3);
	private Timer aniclimb = new Timer((float)0.2);
	private Timer anifall = new Timer((float)0.2);
	private Timer build = new Timer((float)0.6);
	private Timer dig = new Timer((float)0.6);
	private boolean falling;
	private int fallingcounter = 0; 

	private int walkcycle,climbcycle,fallcycle,digcycle =0;
	private String type;
	
	Boolean climbing = false;
	
	public Person(PApplet p, float x, float y) {
		this(p, x, y, "climber");
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
	public int update(Brick[][] collisionMap,ArrayList<Brick> terrain) {
		if (falling){
			fallingcounter++;
		}else{
			if (fallingcounter > 100){
				return 3;
			}
			fallingcounter =0;
				
		}
		
		
		/******************************animation stuff*************************************/
			
		if(climbing){
			aniclimb.update(1/parent.frameRate);
			if (aniclimb.isOver()){
				climbcycle++;
				if (climbcycle == 4){
					climbcycle = 0;
				}
				aniclimb.reset();
			}				
		}else if(falling){
			anifall.update(1/parent.frameRate);
			if (anifall.isOver()){
				fallcycle++;
				if (fallcycle == 4){
					fallcycle = 0;
				}
				anifall.reset();
			}	
		}else if (type.equals("walking")||type.equals("climber")||type.equals("hazmat")){
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
					type = "walking";
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
				if (collisionMap[(int) (x/WIDTH)+j][bottom].getType().equals("lava")) {
					return 3;
				}
				if(collisionMap[(int) (x/WIDTH)+j][bottom].getType().equals("exit")) {
					return 4;
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
				if( collisionMap[front][(int) (y/HEIGHT)+i].getType().equals("exit")) {
					System.out.println("exit");
					return 4;
				}
			}
		}
		
		if(falling){
			if (down) falling = false;
			else y += 40/parent.frameRate;
		}else if(type.equals("climber")) {
			if(ahead){
				y -= 30/parent.frameRate;
				climbing=true;
			}else if (step && climbing){
				y -= 30/parent.frameRate;		
			} else if (ledge && climbing && !step){
				climbing = false;
				y-=HEIGHT;
			}else if (ledge && climbing ){
				y -= 30/parent.frameRate;
			}else if(!down) {
				y += 40/parent.frameRate;
				falling = true;
			}else if (step){
				y-=HEIGHT;
			}else {
				climbing = false;
				x += facing*30/parent.frameRate;
			}
		}else if(type.equals("building")) {
			
			build.update(1/parent.frameRate);
			if (build.isOver()){
				Brick brick; 
				build.reset();
				if (facing==1)
					brick = new Brick(parent, (int)(x/WIDTH)+width+1, (int)(y/HEIGHT)+height-1, tobuild, 1, "green", true);
				else
					brick = new Brick(parent, (int)(x/WIDTH)-tobuild, (int)(y/HEIGHT)+height-1, tobuild, 1, "green", true);				
				terrain.add(brick);
				for(int i = 0 ; i < tobuild ; i ++){
					if (facing==1)
						collisionMap[(int)(x/WIDTH)+(width+i+1)][ (int)(y/HEIGHT)+height-1] = brick;
					else
						collisionMap[(int)(x/WIDTH)-(width+i)][ (int)(y/HEIGHT)+height-1] = brick;

				}
				
			}
		}else if(type.equals("digging")) {
		
			build.update(1/parent.frameRate);
			if (build.isOver()){
				Brick brick; 
				build.reset();
				if (facing==1)
					brick = new Brick(parent, (int)(x/WIDTH)+width+1, (int)(y/HEIGHT)+height-1, tobuild, 1, "green", true);
				else
					brick = new Brick(parent, (int)(x/WIDTH)-tobuild, (int)(y/HEIGHT)+height-1, tobuild, 1, "green", true);				
				terrain.add(brick);
				for(int i = 0 ; i < tobuild ; i ++){
					if (facing==1)
						collisionMap[(int)(x/WIDTH)+(width+i+1)][ (int)(y/HEIGHT)+height-1] = brick;
					else
						collisionMap[(int)(x/WIDTH)-(width+i)][ (int)(y/HEIGHT)+height-1] = brick;

				}
				
			}
		}else {
			if(!down) {
				y += 40/parent.frameRate;			

				falling = true;
			} else {
				if(ahead)facing*=-1;
				else if(step)y-=HEIGHT;
				x += facing*30/parent.frameRate;
			}
		}

		if(y>=parent.height) {
			return 3;
		}
		return 0;

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getFacing() {
		return facing;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void draw() {
		parent.pushMatrix(); 
		parent.scale(facing,1);
		if (falling){
			sprite =images.get("falling").get(fallcycle*92, 0, 92, 128);	
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);

		}else if (climbing){
			System.out.println(climbcycle);
			sprite =images.get("climbing").get(climbcycle*93, 0, 93, 128);
			parent.image(sprite,facing*x-20,y,(int)(facing*width*WIDTH*3),height*HEIGHT);

		}else if (type.equals("digging")){
			System.out.println(digcycle);
			sprite =images.get("digging").get(0, 0, 93, 128);
			parent.image(sprite,facing*x-20,y,(int)(facing*width*WIDTH*3),height*HEIGHT);

		}else if (type.equals("walking")||type.equals("climber")){
			sprite =images.get("sprite").get(walkcycle*54, 0, 54, 128);
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);
		}else if (type.equals("building")){
			sprite =images.get("building").get(walkcycle*105, 0, 105, 128);	
			if (facing==1){
				parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);			
			}else {
				parent.image(sprite,facing*x+10,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);
			}
		}else if (type.equals("hazmat")){
			sprite =images.get("hazmat").get(walkcycle*60, 0, 60, 128);
			parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);
		}
		parent.popMatrix();
	
	}

	public void changeType(String t){
		type = t;
	}
	
	public void build(int x){
		type = "building";
		tobuild = x;
		walkcycle = 0;
	}
	
	public void dig(){
		type = "digging";
		walkcycle = 0;
	}
	
}

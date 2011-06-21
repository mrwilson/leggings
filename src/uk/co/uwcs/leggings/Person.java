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
	private Timer anidig = new Timer((float)0.2);
	private Timer build = new Timer((float)0.6);
	private Timer dig = new Timer((float)0.6);
	private Timer deathtimer = new Timer((float)2);
	private boolean falling;
	private int fallingcounter = 0; 
	private boolean umbrealla= false;
	private boolean climber= false;
	private boolean dead = false;
	private float armx = 0;
	private float army = 0;
	private float legx = 0;
	private float legy = 0;
	private float chestx = 0;
	private float chesty = 0;
	private float headx = 0;
	private float heady = 0;
	private int walkcycle,climbcycle,fallcycle,digcycle =0;
	private String type;
	
	Boolean climbing = false;
	
	public Person(PApplet p, float x, float y) {
		this(p, x, y, "walking");
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
		if(!dead){
			if (falling){
				fallingcounter++;
			}else{
				if (fallingcounter > 100 && !umbrealla){
					dead = true;
				}
				fallingcounter =0;
			}
			
			/******************************animation stuff*************************************/
			if(climbing){
				climbingUpdate();
			}else if(falling){
				fallingupdate();
			}else if(type.equals("building")){
				buildingupdate();
			}else if(type.equals("digging")){
				diggingupdate();
			}else if (type.equals("walking")||climber||type.equals("hazmat")){
				walkingupdate();
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
					if (collisionMap[(int) (x/WIDTH)+j][bottom].getType().equals("lava")&&!type.equals("hazmat")) {
						if(facing ==1&& j==0){
							dead = true;
						}else if (j==1){
							dead = true;
						}
					}
					if(collisionMap[(int) (x/WIDTH)+j][bottom].getType().equals("exit")) {
						return 4;
					}
				}
			}
			int front;
			if(facing == 1) front = (int) (x/WIDTH + width);
			else front = (int) (x/WIDTH );
			
			for(int i=0; i<=height; ++i) {
				if(collisionMap[front][(int) (y/HEIGHT)+i] != null) {
					forwards[i] = true;
					if(i==(height-1))step = true;
					else if(i==height)ledge = true;
					else ahead = true;
					if( collisionMap[front][(int) (y/HEIGHT)+i].getType().equals("exit")) {
						return 4;
					}
				}
			}
			if(falling){
				if (down) falling = false;
				else y += 40/parent.frameRate;
			}else if(type.equals("building")) {
				build.update(1/parent.frameRate);
				if (build.isOver()){
					Brick brick; 
					build.reset();
					String ima ="";
					switch(tobuild){
					case 2: ima = "yellow"; break;
					case 4: ima = "blue2";  break;
					case 6:  ima = "green3";  break;
					default: break;
					}
					if (facing==1)
						brick = new Brick(parent, (int)(x/WIDTH)+width+1, (int)(y/HEIGHT)+height-1, tobuild, 1, ima, true);
					else
						brick = new Brick(parent, (int)(x/WIDTH)-tobuild, (int)(y/HEIGHT)+height-1, tobuild, 1, ima, true);				
					terrain.add(brick);
					for(int i = 0 ; i < tobuild ; i ++){
						if (facing==1)
							collisionMap[(int)(x/WIDTH)+(width+i+1)][ (int)(y/HEIGHT)+height-1] = brick;
						else
							collisionMap[(int)(x/WIDTH)-(width+i)][ (int)(y/HEIGHT)+height-1] = brick;
					}
				}
			}else if(type.equals("digging")) {
				dig.update(1/parent.frameRate);
				if (dig.isOver()){
					Brick brick; 
					dig.reset();
					if (facing==1)
						brick = collisionMap[(int)(x/WIDTH)+(width)][(int)(y/HEIGHT)+height];
					else
						brick = collisionMap[(int)(x/WIDTH)+(width)][(int)(y/HEIGHT)+height];
					if(brick != null){
						for(int i=0; i<brick.getWidth(); ++i) {
							collisionMap[(int)(brick.getX())+i][(int)(brick.getY())] = null;
						}
						terrain.remove(brick);
					}
					
				}
			}else if(climber) {
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
	
			if(y>=400) {
				return 3;
			}
		}
		else{
			armx -= 0.5;
			army -= 0.1-deathtimer.getCurrent()/8;
			legx -= 0.25;
			legy -= 0.1-deathtimer.getCurrent()/8;
			chestx += 0.25;
			chesty -= 0.1-deathtimer.getCurrent()/8;
			headx += 0.5;
			heady -= 0.1-deathtimer.getCurrent()/8;	
			if (deathtimer.isOver()){
				return 3;
			}
			deathtimer.update(1/parent.frameRate);
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
		if (!dead){
			parent.pushMatrix(); 
			parent.scale(facing,1);
			if (falling&&fallingcounter>30){
				if (umbrealla){
					sprite =images.get("umbrella");	
					if (facing == 1)
						parent.image(sprite,facing*x,y-15,(int)(facing*width*WIDTH*3),(int)(height*HEIGHT*1.2));			
					else
						parent.image(sprite,facing*x+20,y-15,(int)(facing*width*WIDTH*3),(int)(height*HEIGHT*1.2));						
				}else{
					sprite =images.get("falling").get(fallcycle*92, 0, 92, 128);	
					if (facing==1){
						parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*2.5),height*HEIGHT);			
					}else {
						parent.image(sprite,facing*x+20,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);
					}
				}
			}else if (climbing){
				sprite =images.get("climbing").get(climbcycle*93, 0, 93, 128);
				parent.image(sprite,facing*x-20,y,(int)(facing*width*WIDTH*3),height*HEIGHT);
	
			}else if (type.equals("building")){
				sprite =images.get("building").get(walkcycle*105, 0, 105, 128);	
				if (facing==1){
					parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);			
				}else {
					parent.image(sprite,facing*x+10,y+2,(int)(facing*width*WIDTH*3),height*HEIGHT);
				}
				
			}else if (type.equals("digging")){
				sprite =images.get("digging").get(0, 0, 93, 128);
				if (facing==1){
					parent.image(sprite,facing*x,y+walkcycle,(int)(facing*width*WIDTH*3),height*HEIGHT);
				}else {
					parent.image(sprite,facing*x,y+walkcycle,(int)(facing*width*WIDTH*3),height*HEIGHT);
				}
			}else if (type.equals("walking")||climber){
				sprite =images.get("sprite").get(walkcycle*54, 0, 54, 128);
				parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);
			}else if (type.equals("hazmat")){
				sprite =images.get("hazmat").get(walkcycle*60, 0, 60, 128);
				parent.image(sprite,facing*x,y+2,(int)(facing*width*WIDTH*1.5),height*HEIGHT);
			}
			parent.popMatrix();
		}else{
			parent.image(images.get("head"), x+headx, y+heady+30,8,8);
			parent.image(images.get("arm"), x+armx, y+army+30,10,8);
			parent.image(images.get("leg"), x+legx, y+legy+30,8,8);
			parent.image(images.get("chest"), x+chestx, y+chesty+30,16,16);
		}
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
	
	public void giveUmbrella(){
		umbrealla = true;
	}
	
	public boolean hasUmbrella(){
		return umbrealla;
	}
	
	public void makeClimber(){
		climber = true;
	}
	
	public boolean isClimber(){
		return climber;
	}
	public void climbingUpdate(){
		aniclimb.update(1/parent.frameRate);
		if (aniclimb.isOver()){
			climbcycle++;
			if (climbcycle == 4){
				climbcycle = 0;
			}
			aniclimb.reset();
		}	
	}
	
	public void fallingupdate(){
		anifall.update(1/parent.frameRate);
		if (anifall.isOver()){
			fallcycle++;
			if (fallcycle == 4){
				fallcycle = 0;
			}
			anifall.reset();
		}
	}
	
	public void walkingupdate(){
		animation.update(1/parent.frameRate);
		if (animation.isOver()){
			walkcycle++;
			if (walkcycle == 8){
				walkcycle = 0;
			}
			animation.reset();
		}
	}
	
	public void buildingupdate(){
		anibuild.update(1/parent.frameRate);
		if (anibuild.isOver()){
			walkcycle++;
			if (walkcycle == 4){
				type = "walking";
			}
			anibuild.reset();
		}
	}
	
	public void diggingupdate(){
		anidig.update(1/parent.frameRate);
		if (anidig.isOver()){
			walkcycle++;
			if (walkcycle == 4){
				type = "walking";
			}
			anidig.reset();
		}
	}
}



package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;

public class World extends Screen {
	PImage gui; 
	ArrayList<Person> people;
	ArrayList<Person> peopletoadd;
	ArrayList<Brick> terrain;
	Brick[][] collisionMap = new Brick[1000][1000];
	HashMap<String,PImage> backgrounds;
	PApplet parent;
	float timeRemaining;
	Timer creationTimer;
	int fourblock = 0;
	int sixblock = 0;
	int twoblock = 0;
	private int rescued = 0;
	private int spawnCount;
	
//note that a Lego brick is of ratio 6:5
	public World(PApplet p)
	{
		people = new ArrayList<Person>();
		terrain = new ArrayList<Brick>();
		backgrounds = new HashMap<String,PImage>();
		this.parent = p;
		try {
			Level level = new Level(parent, new File("../res/oep/level1.oel"));
			terrain = level.getLevelList();
			peopletoadd= level.getPeopleList();
			creationTimer = new Timer(5);
			spawnCount = level.getSpawnAmount();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//terrain.add(new Brick(parent, 336/16, 208/16, 2, 1, "red", true, "lava"));
		//people.add(new Person(parent, 10, 8));
		//people.add(new Person(parent, 12, 8));
		//people.add(new Person(parent, 12, 8, "climber"));
		//people.add(new Person(parent, 24, 8));
		//people.add(new Person(parent, 32, 8));

		backgrounds.put("title", parent.loadImage("../res/images/leggings.png"));
		backgrounds.put("easy", parent.loadImage("../res/images/easybackground.png"));
		backgrounds.put("medium", parent.loadImage("../res/images/mediumbackground.png"));
		backgrounds.put("hard", parent.loadImage("../res/images/hardbackground.png"));
		Person.images.put("default", parent.loadImage("../res/images/IMAG0040.png"));
		Person.images.put("sprite", parent.loadImage("../res/images/legosprite.png"));
		Person.images.put("building", parent.loadImage("../res/images/buildani.png"));
		Person.images.put("climbing", parent.loadImage("../res/images/climbingsprite.png"));
		Brick.images.put("yellow", parent.loadImage("../res/images/yellowblock.png"));
		Brick.images.put("blue", parent.loadImage("../res/images/blueblock.png"));
		Brick.images.put("green", parent.loadImage("../res/images/greenblock.png"));
		Brick.images.put("red", parent.loadImage("../res/images/redblock.png"));
		Brick.images.put("grey", parent.loadImage("../res/images/greyblock.png"));
		Brick.images.put("spawn", parent.loadImage("../res/images/spawn.png"));
		Brick.images.put("exit", parent.loadImage("../res/images/exit.png"));
		
		Iterator<Brick> it = terrain.iterator();
		while(it.hasNext()) {
			Brick currentBrick = it.next();
			if(currentBrick.collidable)
			{
				for(int i=0; i<currentBrick.getWidth(); ++i) {
					for(int j=0; j<currentBrick.getHeight(); ++j) {
						collisionMap[i+currentBrick.getX()][j+currentBrick.getY()] = currentBrick;
					}
				}
			}
		}
		gui = parent.loadImage("../res/images/GUI.png");
		timeRemaining = 30;
		Collections.reverse(terrain);
	}

	public void update()
	{
		if(creationTimer.isOver()){
			if (!peopletoadd.isEmpty()){
					spawnCount--;
					people.add(peopletoadd.remove(0));
					creationTimer.reset();
			}
		}
		creationTimer.update(1/parent.frameRate);
		Iterator<Person> it = people.iterator();
		ArrayList<Person> peopleRemoval = new ArrayList<Person>();
		while(it.hasNext())
		{
			Person temp = it.next();
			int i = temp.update(collisionMap,terrain);
			if (i == 3) {
				peopleRemoval.add(temp);
			}
			else if (i == 4) {
				peopleRemoval.add(temp);
				rescued++;
			}
		}
		people.removeAll(peopleRemoval);
	}

	public void display() {
		parent.image(backgrounds.get("easy"), 0, 0, parent.width, parent.height);
		
		Iterator<Brick> itb = terrain.iterator();
		while(itb.hasNext())
		{
			itb.next().draw();
		}
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().draw();
		}
		parent.image(gui, 0, 400, 800, 200);
		parent.textSize(32);
		parent.text((int) timeRemaining + " - " + rescued, 600, 480);
		parent.fill(0, 102, 153);
		timeRemaining -= (1/parent.frameRate);
		
		if (timeRemaining <= 0) {
			System.exit(0);
		}
	}

	public int mousePressed(int x, int y) {
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			Person man = it.next();
			if (man.getX() <= x && man.getX() >= x -15 ){
				if (man.getY() <= y && man.getY() >= y - 48 ){
					boolean free = true;
					for(int i = 0 ; i < 2 ; i ++){
						if (collisionMap[(int)(man.getX()/10)+man.getWidth()+i*man.getFacing()][ (int)(man.getY()/12)+man.getHeight()-1] !=null) free = false;
					}
					if (free){
						free = false;
						for(int i = 0 ; i < 2 ; i ++){
							if (collisionMap[(int)(man.getX()/10)+man.getWidth()+i*man.getFacing()][ (int)(man.getY()/12)+man.getHeight()] !=null)
								free = true;
						}		
						if (free)
							man.build(2);
					}
						
				} 
			}

			System.out.println("x: "+ x +" y: "+y);
			System.out.println("x: "+ man.getX() +" y: "+man.getY());

		}
		return 0;
		
	}

}

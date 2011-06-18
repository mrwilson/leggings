package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;

public class World extends Screen {
	PImage gui; 
	ArrayList<Person> people;
	ArrayList<Brick> terrain;
	Brick[][] collisionMap = new Brick[1000][1000];
	PImage background;
	PApplet parent;
	float timeRemaining;
	
//note that a Lego brick is of ratio 6:5
	public World(PApplet p)
	{
		people = new ArrayList<Person>();
		terrain = new ArrayList<Brick>();
		this.parent = p;
		try {
			Level level = new Level(parent, new File("../res/oep/level1.oel"));
			terrain = level.getLevelList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		people.add(new Person(parent, 30, 8));
		people.add(new Person(parent, 26, 8));
		people.add(new Person(parent, 24, 8));
		people.add(new Person(parent, 32, 8));

		background = parent.loadImage("../res/images/leggings.png");
		Person.images.put("default", parent.loadImage("../res/images/IMAG0040.png"));
		Brick.images.put("yellow", parent.loadImage("../res/images/yellowblock.png"));
		Brick.images.put("blue", parent.loadImage("../res/images/blueblock.png"));
		Brick.images.put("green", parent.loadImage("../res/images/greenblock.png"));
		Brick.images.put("red", parent.loadImage("../res/images/redblock.png"));
		Brick.images.put("grey", parent.loadImage("../res/images/greyblock.png"));
		Brick.images.put("spawn", parent.loadImage("../res/images/spawn.png"));

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
	}

	public void update()
	{
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().update(collisionMap);
		}
	}

	public void display() {
		parent.image(background, 0, 0, parent.width, parent.height);
		
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
		parent.text((int) timeRemaining, 620, 480);
		parent.fill(0, 102, 153);
		timeRemaining -= (1/parent.frameRate);
	}
}

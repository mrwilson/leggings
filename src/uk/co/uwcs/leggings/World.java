package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PImage;

public class World extends Screen {

	ArrayList<Person> people;
	ArrayList<Brick> terrain;
	Brick[][] collisionMap = new Brick[1000][1000];
	PImage background;
	PApplet parent;
		
//note that a Lego brick is of ratio 6:5
	public World(PApplet p)
	{
		people = new ArrayList<Person>();
		terrain = new ArrayList<Brick>();
		this.parent = p;
		people.add(new Person(parent, 300, 100));
		people.add(new Person(parent, 260, 100));
		people.add(new Person(parent, 240, 100));
		people.add(new Person(parent, 300, 100));
		terrain.add(new Brick(parent, 20, 20));
		terrain.add(new Brick(parent, 24, 20));
		terrain.add(new Brick(parent, 28, 20));
		terrain.add(new Brick(parent, 32, 20));
		terrain.add(new Brick(parent, 36, 20));
		terrain.add(new Brick(parent, 40, 20));
		terrain.add(new Brick(parent, 44, 20));
		terrain.add(new Brick(parent, 48, 20));
		terrain.add(new Brick(parent, 52, 20));
		terrain.add(new Brick(parent, 52, 21));
		terrain.add(new Brick(parent, 52, 22));
		terrain.add(new Brick(parent, 52, 23));
		terrain.add(new Brick(parent, 56, 23));
		terrain.add(new Brick(parent, 60, 23));
		terrain.add(new Brick(parent, 64, 23));
		terrain.add(new Brick(parent, 68, 23));
		terrain.add(new Brick(parent, 68, 22));
		terrain.add(new Brick(parent, 68, 21));
		terrain.add(new Brick(parent, 34, 20));
		terrain.add(new Brick(parent, 22, 20));
		terrain.add(new Brick(parent, 26, 20));
		terrain.add(new Brick(parent, 30, 20));
		terrain.add(new Brick(parent, 38, 20));
		terrain.add(new Brick(parent, 42, 20));
		terrain.add(new Brick(parent, 46, 20));
		terrain.add(new Brick(parent, 50, 20));
		terrain.add(new Brick(parent, 54, 20));
		terrain.add(new Brick(parent, 54, 21));
		terrain.add(new Brick(parent, 54, 22));
		terrain.add(new Brick(parent, 54, 23));
		terrain.add(new Brick(parent, 58, 23));
		terrain.add(new Brick(parent, 62, 23));
		terrain.add(new Brick(parent, 66, 23));
		terrain.add(new Brick(parent, 70, 23));
		terrain.add(new Brick(parent, 70, 22));
		terrain.add(new Brick(parent, 70, 21));
		background = parent.loadImage("../res/images/leggings.png");
		Person.images.put("default", parent.loadImage("../res/images/IMAG0040.png"));
		Brick.images.put("default", parent.loadImage("../res/images/yellowblock.png"));
		Iterator<Brick> it = terrain.iterator();
		while(it.hasNext()) {
			Brick currentBrick = it.next();
			for(int i=0; i<currentBrick.getWidth(); ++i) {
				for(int j=0; j<currentBrick.getHeight(); ++j) {
					collisionMap[i+currentBrick.getX()][j+currentBrick.getY()] = currentBrick;
				}
			}
		}
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
	}
	
}

package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class World extends Screen {

	ArrayList<Person> people;
	ArrayList<Brick> terrain;
	Brick[][] collisionMap = new Brick[100][100];
	PApplet parent;
		
//note that a Lego brick is of ratio 6:5
	public World(PApplet p)
	{
		people = new ArrayList<Person>();
		terrain = new ArrayList<Brick>();
		this.parent = p;
		people.add(new Person(parent, 200, 100));
		Person.images.put("default", parent.loadImage("../res/images/Z0small.jpg"));
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
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().draw();
		}
	}
	
}

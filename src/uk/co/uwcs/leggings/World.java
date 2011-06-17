package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class World extends Screen {
	ArrayList<Person> people;
	PApplet parent;
		
	public World(PApplet p)
	{
		people = new ArrayList<Person>();
		this.parent = p;
		people.add(new Person(parent, 200, 100));
		Person.images.put("default", parent.loadImage("/home/zed0/workspace/leggings/res/images/Z0small.jpg"));
	}
	
	public void update()
	{
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().update();
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

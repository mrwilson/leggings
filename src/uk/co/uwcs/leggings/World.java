package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

public class World extends Screen {
	ArrayList<Person> people;

	public World()
	{
		
	}
	
	public void update()
	{
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().update();
		}
	}

	public void draw() {
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().draw();
		}
	}
}

package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;
import processing.core.*;

public class World extends Screen {
	ArrayList<Person> people;
	PApplet parent;
		
	public World(PApplet p)
	{
		this.parent = p;
	}
	
	public void update()
	{
		/*Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().update();
		}*/
	}

	public void display() {
		/*Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			it.next().draw();
		}
		*/
		parent.stroke(40);
		parent.beginShape();
		parent.line(30, 20, 105, 75);
		parent.endShape();
	}
	
}

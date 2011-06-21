package uk.co.uwcs.leggings;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PImage;

public class WinScreen extends Screen {
	
	PImage background;
	Main parent;
	ArrayList<Button> buttons=new ArrayList<Button>();
	String nextlevel;
	
	WinScreen(Main p,String nextlevel){
		parent = p;
		this.nextlevel = nextlevel;
		if (!nextlevel.equals(""))
			buttons.add( new Button(parent,  "../res/images/start1.png", 220, 400, 361, 106, 1));
		else
			buttons.add( new Button(parent,  "../res/images/start1.png", 220, 400, 361, 106, 1));

		background =  parent.loadImage("../res/images/youwin.png");
		
		
	}
	public void update(){}
	public void display(){	
		parent.image(background, 0, 0, parent.width, parent.height);
		Iterator<Button> it = buttons.iterator();

		if (!nextlevel.equals("")){
			while(it.hasNext()) {
				it.next().draw();
			}
		}

	}
	
	
	public int mousePressed(int x, int y) {
		Iterator<Button> it = buttons.iterator();
		while( it.hasNext()) {
			Button temp = it.next();
			if (temp.isClicked(x, y)) {
				switch(temp.getFlag()) {
				case 1 :  if (!nextlevel.equals(""))parent.changeScreen(new World(parent,"../res/oep/"+ nextlevel));break; 
				case 0 : System.exit(0); break;
				default : break;
				
				}
			}
		}
	return 0;
	}
	

}

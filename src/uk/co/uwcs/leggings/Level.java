package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.xml.XMLElement;

public class Level{
	XMLElement levelXML;
	ArrayList<Brick> levelList;
	
	public Level(PApplet p, File file) throws FileNotFoundException{
		levelXML = new XMLElement(new FileReader(file)).getChild("bricks");
		levelList = new ArrayList<Brick>();
		for(int i = 0; i < levelXML.getChildCount(); i++) {
			String type = levelXML.getChild(i).getName().toString();
			int x = Integer.parseInt(levelXML.getChild(i).getString("x").toString())/16; //divided by 16 due to ogmo grid size
			int y = Integer.parseInt(levelXML.getChild(i).getString("y").toString())/16;
			if(type.equals("spawn")) {
				levelList.add(new Brick(p,x,y,8,6,type,false));
			}
			else {
				levelList.add(new Brick(p,x,y,type));
			}
		}
	}

	public ArrayList<Brick> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<Brick> levelList) {
		this.levelList = levelList;
	}
}
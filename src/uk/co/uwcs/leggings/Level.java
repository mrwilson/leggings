package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.xml.XMLElement;

public class Level {
	XMLElement levelXML;
	ArrayList<Brick> levelList;
	
	public Level(PApplet p, File file) throws FileNotFoundException{
		levelXML = new XMLElement(new FileReader(file)).getChild("bricks");
		levelList = new ArrayList<Brick>();
		for(int i = 0; i < levelXML.getChildCount(); i++) {
			int x = Integer.parseInt(levelXML.getChild(i).getString("x").toString());
			int y = Integer.parseInt(levelXML.getChild(i).getString("y").toString());
			levelList.add(new Brick(p,x,y));
		}
	}

	public ArrayList<Brick> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<Brick> levelList) {
		this.levelList = levelList;
	}
}
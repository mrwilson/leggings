package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.xml.XMLElement;

public class Level{
	XMLElement levelXML;
	ArrayList<Brick> levelList;
	ArrayList<Person> levelPeople;
	int spawnAmount;
	int spawnX;
	int spawnY;
	public Level(PApplet p, File file) throws FileNotFoundException{
		levelXML = new XMLElement(new FileReader(file)).getChild("bricks");
		levelList = new ArrayList<Brick>();
		levelPeople = new ArrayList<Person>();

		for(int i = 0; i < levelXML.getChildCount(); i++) {
			String type = levelXML.getChild(i).getName().toString();
			int x = Integer.parseInt(levelXML.getChild(i).getString("x").toString())/16; //divided by 16 due to ogmo grid size
			int y = Integer.parseInt(levelXML.getChild(i).getString("y").toString())/16;
			if(type.equals("spawn")) {
				levelList.add(new Brick(p,x,y,8,6,type,false,type));
				spawnAmount = Integer.parseInt(levelXML.getChild(i).getString("count").toString());
				spawnX = Integer.parseInt(levelXML.getChild(i).getString("x").toString());
				spawnY = Integer.parseInt(levelXML.getChild(i).getString("y").toString());
			}else if(type.equals("exit")) {
				levelList.add(new Brick(p,x,y,8,6,type,true,type));
			}else if(type.matches(".*2$")) {
				levelList.add(new Brick(p,x,y,4,1,type,true,type));
			}else if(type.matches(".*3$")) {
				levelList.add(new Brick(p,x,y,6,1,type,true,type));
			}else{
				levelList.add(new Brick(p,x,y,type));
			}
		}
		for (int i = 0 ; i < spawnAmount;i++){
			levelPeople.add(new Person(p,(spawnX/16)+1,spawnY/16));
			
		}
		Collections.sort(levelList);
		
		
	}

	public ArrayList<Brick> getLevelList() {
		return levelList;
	}

	public ArrayList<Person> getPeopleList() {
		return levelPeople;
	}

	
	public void setLevelList(ArrayList<Brick> levelList) {
		this.levelList = levelList;
	}
	
	public int getSpawnAmount() {
		return spawnAmount;
	}

	public void setSpawnAmount(int spawnAmount) {
		this.spawnAmount = spawnAmount;
	}

}

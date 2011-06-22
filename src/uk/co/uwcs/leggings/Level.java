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
	int rescueAmount;
	int time;
	int diggers;
	int climbers;
	int umbrellas;
	int hazmats;
	int twoblock;
	int fourblock;
	int sixblock;
	int stoppers;
	
	public int getStoppers() {
		return stoppers;
	}

	String nextlevel;
	public Level(PApplet p, File file) throws FileNotFoundException{
		levelXML = new XMLElement(new FileReader(file));
		nextlevel = levelXML.getString("nextLevel");
		time = Integer.parseInt(levelXML.getString("time"));
		rescueAmount = Integer.parseInt(levelXML.getString("save"));
		diggers= Integer.parseInt(levelXML.getString("diggers"));
		climbers= Integer.parseInt(levelXML.getString("climbers"));
		umbrellas= Integer.parseInt(levelXML.getString("umbrellas"));
		hazmats= Integer.parseInt(levelXML.getString("hazmats"));
		twoblock= Integer.parseInt(levelXML.getString("twoblock"));
		fourblock= Integer.parseInt(levelXML.getString("fourblock"));
		sixblock= Integer.parseInt(levelXML.getString("sixblock"));
		stoppers= Integer.parseInt(levelXML.getString("stoppers"));
		levelXML = levelXML.getChild("bricks");
		levelList = new ArrayList<Brick>();
		levelPeople = new ArrayList<Person>();

		for(int i = 0; i < levelXML.getChildCount(); i++) {
			String type = levelXML.getChild(i).getName().toString();
			int x = Integer.parseInt(levelXML.getChild(i).getString("x").toString())/16; //divided by 16 due to ogmo grid size
			int y = Integer.parseInt(levelXML.getChild(i).getString("y").toString())/16;
			if(type.equals("spawn")) {
				levelList.add(new Brick(p,x,y,8,6,type,false,type));
				spawnAmount = Integer.parseInt(levelXML.getChild(i).getString("count").toString());
				spawnX = Integer.parseInt(levelXML.getChild(i).getString("x").toString())+50;
				spawnY = Integer.parseInt(levelXML.getChild(i).getString("y").toString());
			}else if(type.equals("exit")) {
				levelList.add(new Brick(p,x,y,8,6,type,true,type));
			}else if(type.matches(".*2$")) {
				levelList.add(new Brick(p,x,y,4,1,type,true,type));
			}else if(type.matches(".*3$")) {
				levelList.add(new Brick(p,x,y,6,1,type,true,type));
			}else if(type.equals("lava")) {
				levelList.add(new Brick(p,x,y,2,1,type,true,type));
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

	public int getRescueAmount() {
		return rescueAmount;
	}

	public int getTime() {
		return time;
	}

	public String getNextlevel() {
		return nextlevel;
	}

	public int getDiggers() {
		return diggers;
	}

	public int getClimbers() {
		return climbers;
	}

	public int getUmbrellas() {
		return umbrellas;
	}

	public int getHazmats() {
		return hazmats;
	}

	public int getTwoblock() {
		return twoblock;
	}

	public int getFourblock() {
		return fourblock;
	}

	public int getSixblock() {
		return sixblock;
	}
	

	
}

package uk.co.uwcs.leggings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import processing.core.PImage;

public class World extends Screen {
	PImage gui; 
	PImage win;
	ArrayList<Person> people;
	ArrayList<Person> peopletoadd;
	ArrayList<Person> stoppers= new ArrayList<Person>();
	ArrayList<Brick> terrain;
	ArrayList<Button> buttons;
	Button clickedbutton;
	Brick[][] collisionMap = new Brick[1000][1000];
	HashMap<String,PImage> backgrounds;
	Main parent;
	float timeRemaining;
	Timer creationTimer;
	int fourblock = 0;
	int sixblock = 0;
	int twoblock = 0;
	int diggers = 0;
	int climbers = 0;
	int umberellas = 0;
	int hazmats = 0;
	int numstoppers =10;
	private int rescued = 0;
	private int spawnCount;
	private int rescueAmount;
	String nextLevel;
	String nextType;
	boolean paused = false;
	private int camera = 0;
	String XMLPath;
	
//note that a Lego brick is of ratio 6:5
	public World(Main p, String XMLPath)
	{
		people = new ArrayList<Person>();
		this.XMLPath = XMLPath;
		terrain = new ArrayList<Brick>();
		backgrounds = new HashMap<String,PImage>();
		buttons = new ArrayList<Button>();
		this.parent = p;
		creationTimer = new Timer(3);

		nextType = "default";
		try {
			Level level = new Level(parent, new File(XMLPath));
			terrain = level.getLevelList();
			peopletoadd= level.getPeopleList();
			spawnCount = level.getSpawnAmount();
			rescueAmount = level.getRescueAmount();
			nextLevel = level.getNextlevel();
			timeRemaining = level.getTime();
			fourblock = level.getFourblock();
			sixblock = level.getSixblock();
			twoblock = level.getTwoblock();
			diggers = level.getDiggers();
			climbers = level.getClimbers();
			umberellas = level.getUmbrellas();
			hazmats = level.getHazmats();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		buttons.add(new Button(parent, "../res/images/digger.png", 400-48, 440, 48, 64, 3));
		buttons.add(new Button(parent, "../res/images/hazfaceon.png", 400-48*2, 440, 48, 64, 2));
		buttons.add(new Button(parent, "../res/images/frontonclimber.png", 400-48*3, 440, 48, 64, 1));
		buttons.add(new Button(parent, "../res/images/frontonbuilder.png", 400-48*4, 440, 48, 64, 5));
		buttons.add(new Button(parent, "../res/images/frontonbuilder.png", 400-48*5, 440, 48, 64, 6));
		buttons.add(new Button(parent, "../res/images/frontonbuilder.png", 400-48*6, 440, 48, 64, 7));
		buttons.add(new Button(parent, "../res/images/frontonumbrella.png", 400-48*7, 440, 48, 64, 9));
		buttons.add(new Button(parent, "../res/images/paws.png", 660,520, 48, 64, 8));
		buttons.add(new Button(parent, "../res/images/refresh.png", 600, 520, 48, 64, 10));
		
		backgrounds.put("title", parent.loadImage("../res/images/leggings.png"));
		backgrounds.put("easy", parent.loadImage("../res/images/easybackground.png"));
		backgrounds.put("medium", parent.loadImage("../res/images/mediumbackground.png"));
		backgrounds.put("hard", parent.loadImage("../res/images/hardbackground.png"));
		Person.images.put("default", parent.loadImage("../res/images/IMAG0040.png"));
		Person.images.put("hazmat", parent.loadImage("../res/images/hazmat.png"));
		Person.images.put("sprite", parent.loadImage("../res/images/legosprite.png"));
		Person.images.put("building", parent.loadImage("../res/images/buildani.png"));
		Person.images.put("digging", parent.loadImage("../res/images/digger.png"));
		Person.images.put("umbrella", parent.loadImage("../res/images/umbrella.png"));
		Person.images.put("arm", parent.loadImage("../res/images/arm.png"));
		Person.images.put("leg", parent.loadImage("../res/images/leg.png"));
		Person.images.put("head", parent.loadImage("../res/images/head.png"));
		Person.images.put("chest", parent.loadImage("../res/images/chest.png"));
		Person.images.put("stopper", parent.loadImage("../res/images/stopper.png"));

		Person.images.put("climbing", parent.loadImage("../res/images/climbingsprite.png"));
		Person.images.put("falling", parent.loadImage("../res/images/fallingsprite.png"));

		Brick.images.put("spawn", parent.loadImage("../res/images/spawn.png"));
		Brick.images.put("exit", parent.loadImage("../res/images/exit.png"));
		Brick.images.put("lava", parent.loadImage("../res/images/lava.png"));

		Brick.images.put("yellow", parent.loadImage("../res/images/yellowblock.png"));
		Brick.images.put("blue", parent.loadImage("../res/images/blueblock.png"));
		Brick.images.put("green", parent.loadImage("../res/images/greenblock.png"));
		Brick.images.put("red", parent.loadImage("../res/images/redblock.png"));
		Brick.images.put("grey", parent.loadImage("../res/images/greyblock.png"));

		Brick.images.put("yellow2", parent.loadImage("../res/images/yellowblock2.png"));
		Brick.images.put("blue2", parent.loadImage("../res/images/blueblock2.png"));
		Brick.images.put("green2", parent.loadImage("../res/images/greenblock2.png"));
		Brick.images.put("red2", parent.loadImage("../res/images/redblock2.png"));
		Brick.images.put("grey2", parent.loadImage("../res/images/greyblock2.png"));

		Brick.images.put("yellow3", parent.loadImage("../res/images/yellowblock3.png"));
		Brick.images.put("blue3", parent.loadImage("../res/images/blueblock3.png"));
		Brick.images.put("green3", parent.loadImage("../res/images/greenblock3.png"));
		Brick.images.put("red3", parent.loadImage("../res/images/redblock3.png"));
		Brick.images.put("grey3", parent.loadImage("../res/images/greyblock3.png"));

		Iterator<Brick> it = terrain.iterator();
		while(it.hasNext()) {
			Brick currentBrick = it.next();
			if(currentBrick.collidable)
			{
				for(int i=0; i<currentBrick.getWidth(); ++i) {
					for(int j=0; j<currentBrick.getHeight(); ++j) {
						collisionMap[i+currentBrick.getX()][j+currentBrick.getY()] = currentBrick;
					}
				}
			}
		}
		gui = parent.loadImage("../res/images/GUI.png");
		win = parent.loadImage("../res/images/youwin.png");
		Collections.reverse(terrain);
	}

	public void update()
	{
		if(creationTimer.isOver()){
			if (!peopletoadd.isEmpty()){
					people.add(peopletoadd.remove(0));
					creationTimer.reset();
			}
		}
		creationTimer.update(1/parent.frameRate);
		Iterator<Person> it = people.iterator();
		ArrayList<Person> peopleRemoval = new ArrayList<Person>();
		while(it.hasNext())
		{
			Person temp = it.next();
			int i = temp.update(collisionMap,terrain,stoppers);
			if (i == 3) {
				peopleRemoval.add(temp);
			}
			else if (i == 4) {
				peopleRemoval.add(temp);
				rescued++;
			}
		}
		people.removeAll(peopleRemoval);
	}

	public void display() {
		parent.image(backgrounds.get("easy"), 0, 0, parent.width, parent.height);
		parent.pushMatrix();
		parent.translate(camera, 0);
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

		parent.popMatrix();
		parent.image(gui, 0, 400, 800, 200);
		Iterator<Button> itc = buttons.iterator();
		while( itc.hasNext()) {
			Button temp = itc.next();
			if (temp == clickedbutton)
				temp.draw(1);
			else
				temp.draw(2);
		}
		parent.textSize(20);
		parent.fill(256, 0,0);
		if (twoblock>9)
			parent.text(twoblock,400-48*4+14, 440 +60);
		else
			parent.text(twoblock,400-48*4+24, 440 +60);
		parent.text("2",400-48*4, 440+15);
			
		if (fourblock>9)
			parent.text(fourblock,400-48*5+14, 440 +60);
		else
			parent.text(fourblock,400-48*5+24, 440 +60);	
		parent.text("4",400-48*5, 440+15);
		
		if (sixblock>9)
			parent.text(sixblock,400-48*6+14, 440 +60);		
		else
			parent.text(sixblock,400-48*6+24, 440 +60);	
		parent.text("6",400-48*6, 440+15);	
		
		if (diggers>9)
			parent.text(diggers,400-48+14, 440 +60);		
		else
			parent.text(diggers,400-48+24, 440 +60);		

		if (climbers>9)
			parent.text(climbers,400-48*3+14, 440 +60);		
		else
			parent.text(climbers,400-48*3+24, 440 +60);		
			
		if (umberellas>9)
			parent.text(umberellas,400-48*7+14, 440 +60);
		else
			parent.text(umberellas,400-48*7+24, 440 +60);		
		
		if (hazmats>9)
			parent.text(hazmats,400-48*2+14, 440 +60);		
		else
			parent.text(hazmats,400-48*2+24, 440 +60);	
		parent.textSize(32);
		parent.text((int) timeRemaining , 600, 480);
		parent.fill(0, 102, 153);
		timeRemaining -= (1/parent.frameRate);
		parent.text(rescued+ " / "+rescueAmount,230, 570);	
		
		
		if (timeRemaining <= 0 && rescued >= rescueAmount) {
			parent.background(0);
			parent.image(win, 0, 0, parent.width, parent.height);
		}
		if (rescued >= rescueAmount){
			parent.changeScreen(new WinScreen(parent, nextLevel));
		}
		if (timeRemaining <= 0) {
			parent.changeScreen(new World(parent,XMLPath));
		}
	}

	public int mousePressed(int x, int y) {
		Iterator<Person> it = people.iterator();
		while(it.hasNext())
		{
			Person man = it.next();
			if (man.getX() <= x && man.getX() >= x -15 ){
				if (man.getY() <= y && man.getY() >= y - 48 ){
					if (nextType.equals("hazmat")){
						if (hazmats >0){
							man.changeType(nextType);
							hazmats--;
							break;
						}
					}else if (nextType.equals("climber")&&!man.isClimber()){
						if (climbers > 0){
							man.makeClimber();
							climbers--;
							break;
						}
					}else if (nextType.equals("digger")){
						if (diggers>0){
							if (collisionMap[(int)(man.getX()/10)][(int)(man.getY()/12+4)]!=null){
								Brick b = collisionMap[(int)(man.getX()/10)][(int)(man.getY()/12+4)];
								if (b.getWidth() ==2)
									twoblock++;
								else if (b.getWidth() ==4)
									fourblock++;
								else if (b.getWidth() ==6)
									sixblock++;
								man.dig();
								diggers--;
							}
						}
						break;
					}else if (nextType.equals("umbrella")){
						if (umberellas>0&&!man.hasUmbrella()){
							man.giveUmbrella();
							umberellas--;
						}
						break;
					}else if (nextType.equals("stopper")){
						if (numstoppers>0&&!man.isStopper()){
							man.makeStopper();
							numstoppers--;
							stoppers.add(man);
						}
						break;
					}else if (nextType.equals("2block")){
						if (twoblock>0){
							boolean free = true;
							if (man.getFacing() == 1){
								for(int i = 0 ; i < 2 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 2 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(2);
										twoblock--;			
									}
								}
							}
							else{
								for(int i = 0 ; i < 2 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)-2+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 2 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)-2+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(2);
										twoblock--;			
									}
								}
							}
						}
						break;
					}else if (nextType.equals("4block")){
						if (fourblock>0){
							boolean free = true;
							if (man.getFacing() == 1){
								for(int i = 0 ; i < 4 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 4 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(4);
										fourblock--;			
									}
								}
							}
							else{
								for(int i = 0 ; i < 4 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)-4+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 4 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)-4+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(4);
										fourblock--;
									}
								}
							}
						}
						break;
					}else if (nextType.equals("6block")){
						if (sixblock>0){
							boolean free = true;
							if (man.getFacing() == 1){
								for(int i = 0 ; i < 6 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 6 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)+man.getWidth()+1+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(6);
										sixblock--;			
									}
								}
							}
							else{
								for(int i = 0 ; i < 6 ; i ++){
									if (collisionMap[(int)(man.getX()/Lego.WIDTH)-6+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()-1] !=null)
										free = false;
								}
								if (free){
									free= false;
									for(int i = 0 ; i < 4 ; i ++){
										if (collisionMap[(int)(man.getX()/Lego.WIDTH)-6+i][ (int)(man.getY()/Lego.HEIGHT)+man.getHeight()] !=null)
											free = true;
									}
									if (free){
										man.build(6);
										sixblock--;
									}
								}
							}
							break;
						}
					}
				} 
			}
		}
		Iterator<Button> itb = buttons.iterator();
		while( itb.hasNext() ) {
			Button temp = itb.next();
			if( temp.isClicked(x, y) ) {
				if (clickedbutton == temp)
					clickedbutton = null;
				else clickedbutton = temp;
				
				switch( temp.getFlag() ) {
				case 0 : System.exit(0); break;
				case 1 : nextType = "climber"; break;
				case 2 : nextType = "hazmat"; break;
				case 3 : nextType = "digger"; break;
				case 4 : nextType = "builder"; break;
				case 5 : nextType = "2block"; break;
				case 6 : nextType = "4block"; break;
				case 7 : nextType = "6block"; break;
				case 8 : pause();break;
				case 9 : nextType = "umbrella"; break;
				case 10 : parent.changeScreen(new World(parent,XMLPath));break;
				case 11 : nextType = "stopper"; break;
				case 12 : nextType = "kill"; 
				if (clickedbutton == temp) temp.draw(1); break;
				default : break;
				}
			}
		}
		return 0;
	}

	public void pause() {
		paused = !paused;
		if (paused) {
			parent.noLoop(); 
		}
		else { 
			parent.loop();
 		}
	}
}

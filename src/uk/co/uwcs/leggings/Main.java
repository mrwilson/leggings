package uk.co.uwcs.leggings;
import processing.core.PApplet;

public class Main extends PApplet{
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 12;
	private static final long serialVersionUID = 1L;
	public static Screen world;
	boolean paused = false;
	private static boolean change = false;
	private static Screen nextScreen;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });

	}
	
	public void setup() {
		world = new SplashScreen(this);
		size(800,600);
		background(0);
		loop();
	}
	
	public void draw() {
		background(0);
		world.update();
		world.display();
		if (change) {
			world = nextScreen;
			change = false;
		}
	}
	
	public void changeScreen(Screen screen) {
		change = true; nextScreen = screen;
	}
	
	public void mouseClicked() {
		int action = world.mousePressed(mouseX, mouseY);
		switch (action) {
		case (-1): break;
		default: break;
		
		}
	}
}

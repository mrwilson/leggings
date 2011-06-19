import processing.core.PApplet;
import uk.co.uwcs.leggings.Screen;
import uk.co.uwcs.leggings.SplashScreen;
import uk.co.uwcs.leggings.World;

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
	
	public void mouseClicked() {
		int action = world.mousePressed(mouseX, mouseY);
		switch (action) {
		case (-1): change = true; nextScreen = new World(this, "../res/oep/level1.oel"); break;
		default: break;
		
		}
	}
}

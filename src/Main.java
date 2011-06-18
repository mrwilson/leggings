import processing.core.PApplet;
import uk.co.uwcs.leggings.World;


public class Main extends PApplet{
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 12;
	private static final long serialVersionUID = 1L;
	public static World world;
	private static boolean paused = false;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });

	}
	
	public void setup() {
		world = new World(this);
		size(800,600);
		background(0);
		//loop();
	}
	
	public void draw() {
		background(0);
		world.update();
		world.display();

	}
	
	public void keyPressed() {
		if ( keyPressed && key == ENTER) {
			paused = !paused;
		}
		if (paused) noLoop(); else loop();
	}
	
}
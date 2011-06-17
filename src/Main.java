import processing.core.PApplet;
import uk.co.uwcs.leggings.World;

public class Main extends PApplet{
		
	private static final long serialVersionUID = 1L;
	public static World world;
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });
	}
	
	public void setup() {
		world = new World(this);
		size(800,600);
		background(0);
		loop();
	}
	
	public void draw() {
		world.update();
		world.display();
	}
}
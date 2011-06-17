import processing.core.*;
import uk.co.uwcs.leggings.Screen;

public class Main extends PApplet{
		
	public static Screen screen;
	
	public void setup() {
		size(800,600);
		
	}
	
	public void draw() {
		screen.update();
		screen.draw();
		
		
	}
	
	
	
}

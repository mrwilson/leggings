package uk.co.uwcs.leggings;

public class Timer {
	
	private float time;
	private float current;
	
	public Timer(float t){
		time = t;
	}

	public void update(float delta){
		current +=delta;
	}
	
	public Boolean isOver(){
		return current >= time;
	}
	
	public void reset(){
		current = 0;
	}
}

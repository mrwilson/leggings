package uk.co.uwcs.leggings;

public class Timer {
	
	private float time;
	private float current=0;
	
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
	
	public float getCurrent() {
		return current;
	}

	public void setCurrent(float current) {
		this.current = current;
	}

}

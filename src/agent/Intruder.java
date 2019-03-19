package agent;

import java.awt.Point;

public class Intruder extends Agent{
	
	private double baseSpeed = 1.4;
	private int angle;
	//visual range 7.5 m
	
	public Intruder(Point position) {
		this.position = position;
	}
	
	@Override
	public void turn(int newAngle) {
		double duration = (newAngle-angle)/180;
		angle = newAngle;
	}
	
	public void sprint() { //max 5 s sprint at 3m/s + 10 s of rest
		
	}

	@Override
	public void walk(int time) {
		// TODO Auto-generated method stub
		
	}

	
}
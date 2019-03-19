package agent;

import java.awt.Point;

public abstract class Agent{
	
	public int angle;
	public Point position;
	
	
	
	public abstract void walk(int time); 
	
	public abstract void turn(int angle);

	public void walkingTurn(int newAngle, int time) {
		// TODO Auto-generated method stub
		
	}
	
}
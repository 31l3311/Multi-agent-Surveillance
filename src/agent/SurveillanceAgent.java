

import java.awt.Point;
import java.util.Vector;

public class SurveillanceAgent extends Agent{
	
	//speed per millisecond
	private double baseSpeed = 0.0014;
	private Point vector;
	//defined as coordinates x,y to 2 decimal places
	private Point position;
	//visual range 6 m
	
	public SurveillanceAgent(Point position) {
		this.position = position;
		vector = new Point(6, 6);
	}
	
	public void updatePos() {
		
	}
	
	@Override
	public void turn(int newAngle) {
		double duration = (newAngle-angle)/180;
		angle = newAngle;
	}

	@Override
	public void walk(int time) {
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		//System.out.println("U: " + u);
		//System.out.println(time*baseSpeed);
		//System.out.println("Vector: " + vector.x + ", " + vector.y);
		position.x += 100*(u*vector.x);
		position.y += 100*(u*vector.y);
		//System.out.println("Position: " + position.x + ", " + position.y);
	}


	
}
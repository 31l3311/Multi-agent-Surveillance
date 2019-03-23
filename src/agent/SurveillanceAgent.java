package agent;

import java.awt.Point;
import java.util.ArrayList;

public class SurveillanceAgent extends Agent{
	
	//speed per millisecond
	public double baseSpeed = 0.0014;
	public double speed;
	private int seeLength = 6000;
	private Point vector;
	private double angle;
	//defined as coordinates x,y in millimeters
	//looking vectors
	

	//visual range 6 m
	
	private double tempAngle = 0;
	private Point tempVector = new Point();


	public SurveillanceAgent(Point position, int time) {
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
	}
	
	public ArrayList update() {
		move(time);
		return look();

	}

	public ArrayList update(double newAngle) {
		move(time);
		vector = movingTurn(newAngle, time, vector);
		return look();
	}

	public ArrayList look() {
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		System.out.println("Position in sur agent: " + position.x + ", " + position.y);
		myDirection = checkVectorSight(vector, seeLength);
		seenSquares.addAll(myDirection);
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength));
		return seenSquares;
	}


	@Override
	public void move(int time) {
		System.out.println("Current Position: " + position.x + ", " + position.y);
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		System.out.println("Updated Position: " + position.x + ", " + position.y);
	}

	public int[] getTranslation() {

		int[] translation = new int[2];
		int actual_x = (int) getPosition().getX();
		int actual_y = (int) getPosition().getY();

		int locX = ( (int) (getPosition().getX())/1000);
		int locY = ( (int) (getPosition().getY())/1000);

		//calculation of pixels to square

		int closest_x = (locX - 1) * 20 * 50;
		int closest_y = (locY - 1) * 20 * 50;

		int x_in_pix = Math.abs(closest_x - actual_x);
		int y_in_pix = Math.abs(closest_y - actual_y);

		translation[0] = x_in_pix;
		translation[1] = y_in_pix;
		System.out.println("GETS HERE 2!");
		return translation;
	}
}
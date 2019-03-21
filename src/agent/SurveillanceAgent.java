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
	ArrayList<Point> seenSquares;
	ArrayList<Point> myDirection = new ArrayList<Point>();

	//visual range 6 m
	
	private double tempAngle = 0;
	private Point tempVector = new Point();

	private int[][] map = new int[200][200];

	public SurveillanceAgent(Point position) {
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
	}
	
	public ArrayList update(int time) {
		move(time);
		return look();
	}

	public ArrayList update(int time, double newAngle) {
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



	public void updateMap(int x, int y, int i) {
		map[x][y] = i;
		if(i == 1) {
			// implement method to avoid tree or shit
			System.out.println("I see a tree!");
		}
	}


	@Override
	public void move(int time) {
		System.out.println("Current Position: " + position.x + ", " + position.y);
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		System.out.println("Updated Position: " + position.x + ", " + position.y);
	}




	
}
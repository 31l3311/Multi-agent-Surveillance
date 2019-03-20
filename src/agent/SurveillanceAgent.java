package agent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class SurveillanceAgent extends Agent{
	
	//speed per millisecond
	private double baseSpeed = 0.0014;
	private int seeLength = 6000;
	private Point vector;
	private double angle;
	//defined as coordinates x,y in millimeters
	private Point position;
	//looking vectors
	ArrayList<Point> seenSquares;
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
		seenSquares.clear();
		seenSquares.addAll(checkVectorSight(vector, seeLength, position));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength, position));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength, position));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength, position));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength, position));
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
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
	}




	
}
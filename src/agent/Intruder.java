import java.awt.Point;
import java.util.ArrayList;

public class Intruder extends Agent{
	
	private double baseSpeed = 1.4;
	private double sprintSpeed = 3;
	private double angle;
	private double sprintAngle;
	private int seeLength = 7500;
	private Point vector;
	private int timeSprinted;
	private int timeWalked;
	private boolean sprint;
	
	private ArrayList<Point> seenSquares;
	//visual range 7.5 m
	
	public Intruder(Point position) {
		this.position = position;
	}
	
	public ArrayList update(int time, boolean s) {
		if(sprint != s && s == true) {
			if(timeWalked>= (10000/time)) {
			timeSprinted = 0;
			timeWalked = 0;
			sprint = s;
			}
		}
		move(time);
		return look();
	}
	
	public ArrayList update(int time, boolean s, double newAngle) {
		if(sprint != s && s == true) {
			if(timeWalked>= (10000/time)) {
			timeSprinted = 0;
			timeWalked = 0;
			sprint = s;
			sprintAngle = 0;
			}
		}
		move(time);
		if(sprint == true && (sprintAngle + Math.abs(newAngle - angle))<10) {
			vector = movingTurn(newAngle, time, vector);
			sprintAngle += Math.abs(newAngle - angle);
			}
		else if (sprint == false) {
			vector = movingTurn(newAngle, time, vector);}
		
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


	@Override
	public void move(int time) {
		double u = 0;
		if(timeWalked >= (10000/time) && timeSprinted < (5000/time) && sprint == true) {
			u = ((time*sprintSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
			timeSprinted++;
		}
		else {
			u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
			timeWalked++;
		}
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
	}

	
}
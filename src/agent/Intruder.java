package agent;



import java.awt.Point;
import java.util.ArrayList;

public class Intruder extends Agent{
	
	private double baseSpeed = 1.4;
	private double sprintSpeed = 3;
	private double sprintAngle;
	private int seeLength = 7500;
	private int timeSprinted;
	private int timeWalked;
	private boolean sprint;
	private ArrayList<Point> seenSquares;
	//visual range 7.5 m
	
	public Intruder(Point position, int time, Point size) {
		this.position = position;
		this.time = time;
		this.size = size;
	}
	
	public ArrayList update() {
		move(time);
		return look();
	}
	
	public ArrayList update(double newAngle) {
	
		move(time);
		if(sprint == true && (sprintAngle + Math.abs(newAngle - angle))<10) {
			vector = movingTurn(newAngle);
			sprintAngle += Math.abs(newAngle - angle);
			}
		else if (sprint == false) {
			vector = movingTurn(newAngle);}
		
		return look();
	}
	
	public void changeSprint(boolean s) {
		if(sprint != s && s == true) {
			if(timeWalked>= (10000/time)) {
			timeSprinted = 0;
			timeWalked = 0;
			sprint = s;
			sprintAngle = 0;
			}
		}
	}
	
	public ArrayList look() {
		seenSquares.clear();
		seenSquares.addAll(checkVectorSight(vector, seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength));
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

	@Override
	public ArrayList update(boolean stop, double newAngle) {
		// TODO Auto-generated method stub
		return null;
	}
}
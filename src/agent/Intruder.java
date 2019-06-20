import java.awt.Point;
import java.util.ArrayList;

public class Intruder extends Agent{

	private double sprintSpeed = 0.003;
	private double sprintAngle;
	private int seeLength = 7500;
	private int seeLengthSentry = 18000;
	private int seeLengthObjects = 10000;
	private int timeSprinted;
	private int timeWalked;
	private boolean sprint;
	private ArrayList<Point> seenSquares;
	private boolean stop;
	private int counter;
	//visual range 7.5 m
	
	public Intruder(Point position, int time, Point size) {
		speed = BASESPEED;
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
		this.size = size;
	}
	
	public ArrayList update() {
		if(openDoor) {
			counter++;
			if(counter>= (doorTime*1000)/time) {
				openDoor = false;
				loudDoor = false;
				counter = 0;
			}
		}
		if(openWindow) {
			counter++;
			if(counter>= (3*1000)/time) {
				openWindow = false;
				counter = 0;
			}
		}
		if(openDoor == false && openWindow == false) {
		if(stop == false) {
			move(time);}
		if(angle != newAngle) {
			vector = movingTurn(newAngle);
		}
		else {
			stop = false;
		}
		
	}
		return look();
		}
	
	public ArrayList update(double newAngle) {
		if(openDoor) {
			counter++;
			if(counter>= (doorTime*1000)/time) {
				openDoor = false;
				loudDoor = false;
				counter = 0;
			}
		}
		if(openWindow) {
			counter++;
			if(counter>= (3*1000)/time) {
				openWindow = false;
				counter = 0;
			}
		}
		if(openDoor == false && openWindow == false) {
		move(time);
		if(sprint == true && (sprintAngle + Math.abs(newAngle - angle))<10) {
			vector = movingTurn(this.newAngle);
			sprintAngle += Math.abs(newAngle - angle);
			}
		else if (sprint == false) {
			this.newAngle = gon(newAngle);
			vector = movingTurn(this.newAngle);}
		}
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
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		myDirection = checkVectorSight(vector, seeLength, seeLengthSentry, seeLengthObjects);
		seenSquares.addAll(checkVectorSight(vector, seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength, seeLengthSentry, seeLengthObjects));
		return seenSquares;
	}


	@Override
	public void move(int time) {
		double u;
		if(timeWalked >= (10000/time) && timeSprinted < (5000/time) && sprint == true) {
			u = ((time*sprintSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
			timeSprinted++;
		}
		else {
			u = ((time*speed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
			timeWalked++;
		}
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		//System.out.println("Position in move method:" + position);
	}

	@Override
	public ArrayList update(boolean stop, double newAngle) {
		// TODO Auto-generated method stub
		this.stop = stop;
		vector = movingTurn(gon(newAngle));
		return look();
	}
}
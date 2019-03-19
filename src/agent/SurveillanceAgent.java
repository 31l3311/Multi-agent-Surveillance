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
	private Point lastSquare = new Point(0,0);
	private double posX,posY;
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
		walk(time);
		//walkingTurn(time);
		return look();
	}
	public void checkVectorSight(Point seeVector) {
		posX = position.x;
		posY = position.y;
		double u = (seeLength*0.1)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		System.out.println("U: " + u);
		for(int i = 0; i<10; i++) {
			posX += (u*seeVector.x);
			posY += (u*seeVector.y);
		System.out.println("posX and Y: " + posX + ", " + posY);
		Point newSquare = new Point((int)(posX/1000),(int)(posY/1000));
		if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y) {
			System.out.println("New square: " + newSquare.x + ", " + newSquare.y + "  last square: " + lastSquare.x + ", " + lastSquare.y);
			lastSquare = newSquare;
			System.out.println("lastSquare: " + lastSquare.getX() + ", " + lastSquare.getY());
			seenSquares.add(lastSquare);
		}}
	System.out.println("checkVector finished");
	}
	
	public ArrayList look() {
		seenSquares.clear();
		checkVectorSight(vector);
		checkVectorSight(findVector(gon(angle + 11.25)));
		checkVectorSight(findVector(gon(angle + 22.5)));
		checkVectorSight(findVector(gon(angle - 11.25)));
		checkVectorSight(findVector(gon(angle - 22.5)));
		return seenSquares;
	}
	
	public void updateMap(int x, int y, int i) {
		map[x][y] = i;
		if(i == 1) {
			// implement method to avoid tree or shit
			System.out.println("I see a tree!");
		}
	}
	
	//method which normalizes angles to values btwn 0 and 360
	public double gon(double angle) {
		if(angle>360)
			angle = angle - 360;
		if(angle<0)
			angle = angle + 360;
	return angle;
	}
	
	public double findAngle(Point vector) {
		if(vector.x>=0 && vector.y >=0)
			tempAngle = (180/Math.PI)*Math.atan(vector.y/vector.x);
		if(vector.x<0 && vector.y >=0)
			tempAngle = 180 - (180/Math.PI)*Math.atan(vector.y/(-vector.x));
		if(vector.x>=0 && vector.y <0)
			tempAngle = 360 - (180/Math.PI)*Math.atan(-vector.y/vector.x);
		if(vector.x<0 && vector.y<0)
			tempAngle = 180 + (180/Math.PI)*Math.atan(vector.y/vector.x);
	return tempAngle;
	}
	
	public Point findVector(double angle) {
		System.out.println("angle: " + angle);
		if(angle< 90) {
			System.out.println("Yay angle is under 90");
			tempVector.y = (int) (Math.tan((Math.PI*angle)/180)*10000);
			tempVector.x = 10000;}
		if(angle == 90) {
			tempVector.y = 1;
			tempVector.x = 0;}
		if(angle>90 && angle <= 180) {
			tempVector.y= (int) (- Math.tan(Math.PI-(Math.PI*angle)/180)*10000);
			tempVector.x = -10000;}
		if(angle>180 && angle<270) {
			tempVector.y = (int) (-Math.tan(Math.PI*(2 - angle/180))*10000);
			tempVector.x = -10000;
		}
		if(angle == 270) {
			tempVector.y = -1;
			tempVector.x = 0;}
		if(angle>270) {
			tempVector.y= (int) (Math.tan(Math.PI*(1+ (angle/180)))*10000);
			tempVector.x = 10000;
		}
		System.out.println("Vector: " + tempVector.x + ",  " + tempVector.y);
	return tempVector;
	}
	
	@Override
	public void walkingTurn(int newAngle, int time) {
		angle = findAngle(vector);
		if(angle<=180) {
		if(newAngle>angle && newAngle<(angle+180)) {
			if(newAngle-angle < 0.180*time)
				angle = newAngle;
			else {
				angle = angle + 0.180*time;
			}
		}
		else {
			if(newAngle > angle)
				newAngle = 360 - newAngle;
			if(angle-newAngle < 0.180*time)
				angle = gon(newAngle);
				//if(angle<0)
					//angle = angle+360;
			else {
				angle = angle - 0.180*time;
			}
		}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if(newAngle-angle < 0.180*time)
					angle = newAngle;
				else {
					angle = angle + 0.180*time;
				}
			}
			else {
				if(newAngle < angle)
					newAngle = 360 + newAngle;
				if(newAngle- angle < 0.180*time)
					angle = gon(newAngle);
					//if(angle>360)
						//angle = angle-360;
				else {
					angle = angle + 0.180*time;
				}
			}	
		}
		vector = findVector(angle);
	}

	@Override
	public void walk(int time) {
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		System.out.println("U: " + u);
		System.out.println(time*baseSpeed);
		System.out.println("Vector: " + vector.x + ", " + vector.y);
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		System.out.println("Position: " + position.x + ", " + position.y);
	}

	@Override
	public void turn(int angle) {
		// TODO Auto-generated method stub
		
	}


	
}
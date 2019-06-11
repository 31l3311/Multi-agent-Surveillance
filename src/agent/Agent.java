package agent;

import java.awt.Point;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;

import Bots.Bot;

import org.apache.commons.math3.*;

public abstract class Agent{
	
	public double angle;
	protected double newAngle;
	protected Point vector;
	public double baseSpeed = 0.0014;
	public Point position;
	private Point lastSquare = new Point(0,0);
	private ArrayList<Point> checkSight = new ArrayList<Point>();
	private double distance;
	private Point newSquare;
	private Point temppos = new Point();
	protected int time;
	protected Point size;
	private Point direction = new Point();
	
	ArrayList<Point> seenSquares;
	public ArrayList<Point> myDirection = new ArrayList<Point>();
	private double speed;

	public abstract void move(int time);
	//public abstract void turn(int angle);
	
	public abstract ArrayList update();
	public abstract ArrayList update(double angle);
	public abstract ArrayList update(boolean stop, double newAngle);
	
	public Point movingTurn(double newAngle) {
		//System.out.println("START OF TURN");
		//System.out.println("Angle = " + angle);
		//System.out.println("New angle = " + newAngle);
		
		if(angle<= 180) {
		if(newAngle>angle && newAngle<(angle+180)) {
			if(Math.abs(newAngle-angle) < 0.180*time) {
				//System.out.println("Angle is smaller than 9 degrees 1");
				angle = newAngle;}
			else {
				angle = angle + 0.180*time;
				//System.out.println("Updated angle 1 : " + angle);
			}
		}
		else {
			if(newAngle > angle) {
				newAngle = newAngle - 360;}
			//System.out.println("Angle check: " + angle);
			//System.out.println("New angle check: " + newAngle);
			if(Math.abs(angle-newAngle) < 0.180*time) {
				angle = gon(newAngle);
				//System.out.println("Angle is smaller than 9 degrees 2");	
			}
			else {
				angle = angle - 0.180*time;
				//System.out.println("Updated angle 2 : " + angle);
			}
		}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if((angle - newAngle) < 0.180*time) {
					//System.out.println("Angle is smaller than 9 degrees 3");
					angle = newAngle;}
				else {
					angle = angle - 0.180*time;
					//System.out.println("Updated angle 3 : " + angle);
				}
			}
			else {
				if(newAngle < angle) {
					newAngle = 360 + newAngle;}
				if(newAngle- angle < 0.180*time) {
					angle = gon(newAngle);
					//System.out.println("Angle is smaller than 9 degrees 4");
				}
					//if(angle>360)
						//angle = angle-360;
				else {
					angle = gon(angle + 0.180*time);
					//System.out.println("Updated angle 4 : " + angle);
				}
			}	
		}
		vector = findVector(angle);
		return vector;
	}
	
	public ArrayList<Point> checkVectorSight(Point seeVector, int seeLength) {
		//System.out.println("position x and Y: " + position.x + ", " + position.y);
		checkSight.clear();
		//u creates a vector in same direction with correct length
		double u = (seeLength)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		temppos.x = position.x;
		temppos.y = position.y;
		//System.out.println("U: " + u);
		for(int i = 0; i<10; i++) {
			temppos.x += 0.1*(u*seeVector.x);
			temppos.y += 0.1*(u*seeVector.y);
		//System.out.println("posX and Y: " + temppos.x + ", " + temppos.y);
		newSquare = new Point((int)(temppos.x/1000),(int)(temppos.y/1000));
		if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y) {
			lastSquare = newSquare;
			//System.out.println("lastSquare: " + lastSquare.getX() + ", " + lastSquare.getY());
		if(lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
			checkSight.add(lastSquare);}
		}
		}
		return checkSight;
	}
	
	public double hear(ArrayList<Bot> bots) {
		for(int i = 0; i<bots.size(); i++) {
			Agent curAgent = bots.get(i).getAgent();
		distance = Math.sqrt(Math.pow((position.x + curAgent.getPosition().x), 2) + Math.pow((position.y + curAgent.getPosition().y), 2));
		if((curAgent.speed < 0.5 && distance<1000) ||
		   (curAgent.speed >= 0.5 && curAgent.speed<1 && distance<3000)	||
		   (curAgent.speed >= 1 && curAgent.speed<2 && distance<5000) ||
		   (curAgent.speed >= 2 && distance<10000)
				) {
			Point vector = new Point(position.x - curAgent.getPosition().x, position.y - curAgent.getPosition().y);
			double angle = findAngle(vector);
			NormalDistribution normal = new NormalDistribution(angle, 10);
			double direction = normal.sample();
			if(direction == 0) {direction = 360;}
			return direction;
		}}
		return 0;
		}
	
	public double findAngle(Point vector) {
		double tempAngle = 0;
		if(vector.x>0 && vector.y >=0)
			tempAngle = (180/Math.PI)*Math.atan(vector.y/vector.x);
		if(vector.x<0 && vector.y >=0)
			tempAngle = 180 - (180/Math.PI)*Math.atan(vector.y/(-vector.x));
		if(vector.x>0 && vector.y <0)
			tempAngle = 360 - (180/Math.PI)*Math.atan(-vector.y/vector.x);
		if(vector.x<0 && vector.y<0)
			tempAngle = 180 + (180/Math.PI)*Math.atan(vector.y/vector.x);
		if(vector.x==0 && vector.y>0)
			tempAngle = 90;
		if(vector.x==0 && vector.y<0)
			tempAngle = 270;
	return tempAngle;
	}

	public Point findVector(double angle) {
		Point tempVector = new Point();
		if(angle< 90) {
			tempVector.y = (int) (Math.tan((Math.PI*angle)/180)*10000);
			tempVector.x = 10000;}
		if(angle == 90) {
			tempVector.y = 1;
			tempVector.x = 0;}
		if(angle>90 && angle <= 180) {
			tempVector.y= (int) (Math.tan(Math.PI-(Math.PI*angle)/180)*10000);
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
	return tempVector;
	}

	//method which normalizes angles to values btwn 0 and 360
	public double gon(double angle) {
		if(angle>360)
			angle = angle - 360;
		if(angle<0)
			angle = angle + 360;
	return angle;
	}

	public Point findVectorPath(Point targetPos){
		return new Point(targetPos.x - this.getCoordinates().x, targetPos.y - this.getCoordinates().y);
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	public Point getPosition() {
		return position;
	}
	
	public Point getCoordinates() {
		return new Point((int)(position.x/1000),(int)(position.y/1000));
	}
	
	public double getAngle() {
		return angle;
	}
	
	public Point direction() {
		double u = ((1000*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		direction.x = (int) (position.x + Math.round(1000*(u*vector.x)));
		direction.y = (int) (position.y + Math.round(1000*(u*vector.y)));
		return direction;
	}
	
}
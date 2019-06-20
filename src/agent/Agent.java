package agent;

import java.awt.Point;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import board.*;


import Bots.Bot;
import board.MainApp;
import board.Square;

import org.apache.commons.math3.*;

public abstract class Agent{
	
	
	public double angle;

	protected double newAngle;
	protected Point vector;
	public final double BASESPEED = 0.0014;
	public final double SLOWSPEED = 0.99;
	public final double VERYSLOWSPEED = 0.49;
	public Point position;
	private Point lastSquare = new Point(0,0);
	private ArrayList<Point> checkSight = new ArrayList<Point>();
	private double distance;
	private Point newSquare;
	private Point temppos = new Point();
	protected int time;
	protected Point size;
	private Point direction = new Point();
	public boolean enterTower;
	public boolean entered;
	public boolean leaveTower;
	public boolean shade;
	public double speed;
	protected double doorTime;
	protected boolean openDoor;
	public boolean openWindow;
	public boolean loudDoor;
	private Square square = new Square();
	ArrayList<Point> seenSquares;
	public ArrayList<Point> myDirection = new ArrayList<Point>();
	public ArrayList<Point> seenIntruders = new ArrayList<Point>();
	protected boolean fastTurn = true;
	
	private double distance1;

	public abstract void move(int time);
	//public abstract void turn(int angle);
	
	public abstract ArrayList update();
	public abstract ArrayList update(double angle);
	public abstract ArrayList update(boolean stop, double newAngle);
	protected int turnCounter;
	
	public Point movingTurn(double newAngle) {
		//System.out.println("START OF TURN");
		//System.out.println("Angle = " + angle);
		//System.out.println("New angle = " + newAngle);
		if(angle <= 180) {
		if(newAngle>angle && newAngle<(angle+180)) {
			if(Math.abs(newAngle-angle) < 0.045*time) {
				//System.out.println("Angle is smaller than 9 degrees 1");
				angle = newAngle;}
			else {
				angle = angle + 0.045*time;
				//System.out.println("Updated angle 1 : " + angle);
			}
		}
		else {
			if(newAngle > angle) {
				newAngle = newAngle - 360;}
			//System.out.println("Angle check: " + angle);
			//System.out.println("New angle check: " + newAngle);
			if(Math.abs(angle-newAngle) < 0.045*time) {
				angle = gon(newAngle);
				//System.out.println("Angle is smaller than 9 degrees 2");
			}
			else {
				angle = angle - 0.045*time;
				//System.out.println("Updated angle 2 : " + angle);
			}
		}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if((angle - newAngle) < 0.045*time) {
					//System.out.println("Angle is smaller than 9 degrees 3");
					angle = newAngle;}
				else {
					angle = angle - 0.045*time;
					//System.out.println("Updated angle 3 : " + angle);
				}
			}
			else {
				if(newAngle < angle) {
					newAngle = 360 + newAngle;}
				if(newAngle- angle < 0.045*time) {
					angle = gon(newAngle);
					//System.out.println("Angle is smaller than 9 degrees 4");
				}
					//if(angle>360)
						//angle = angle-360;
				else {
					angle = gon(angle + 0.045*time);
					//System.out.println("Updated angle 4 : " + angle);
				}
			}
		}
		vector = findVector(angle);
		return vector;
	}

	public Point fastTurn(double newAngle) {
		//System.out.println("START OF TURN");
		//System.out.println("Angle = " + angle);
		//System.out.println("New angle = " + newAngle);
		fastTurn = true;
		turnCounter = 0;
		if(angle <= 180) {
			if(newAngle>angle && newAngle<(angle+180)) {
				if(Math.abs(newAngle-angle) < 0.18*time) {
					//System.out.println("Angle is smaller than 9 degrees 1");
					angle = newAngle;}
				else {
					angle = angle + 0.18*time;
					//System.out.println("Updated angle 1 : " + angle);
				}
			}
			else {
				if(newAngle > angle) {
					newAngle = newAngle - 360;}
				//System.out.println("Angle check: " + angle);
				//System.out.println("New angle check: " + newAngle);
				if(Math.abs(angle-newAngle) < 0.18*time) {
					angle = gon(newAngle);
					//System.out.println("Angle is smaller than 9 degrees 2");
				}
				else {
					angle = angle - 0.18*time;
					//System.out.println("Updated angle 2 : " + angle);
				}
			}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if((angle - newAngle) < 0.18*time) {
					//System.out.println("Angle is smaller than 9 degrees 3");
					angle = newAngle;}
				else {
					angle = angle - 0.18*time;
					//System.out.println("Updated angle 3 : " + angle);
				}
			}
			else {
				if(newAngle < angle) {
					newAngle = 360 + newAngle;}
				if(newAngle- angle < 0.18*time) {
					angle = gon(newAngle);
					//System.out.println("Angle is smaller than 9 degrees 4");
				}
				//if(angle>360)
				//angle = angle-360;
				else {
					angle = gon(angle + 0.18*time);
					//System.out.println("Updated angle 4 : " + angle);
				}
			}
		}
		vector = findVector(angle);
		return vector;
	}

	public void changeTurnSpeed(boolean turnSpeed){
		fastTurn = turnSpeed;
	}

	public double hear() {
		//System.out.println("Hear method called");
		
		for(int i = 0; i<MainApp.bots.size(); i++) {
			if(!MainApp.bots.get(i).getAgent().equals(this)) {
			Agent curAgent = MainApp.bots.get(i).getAgent();
			//System.out.println("Checking agent: " + curAgent);
		distance1 = MainApp.bots.get(i).distance(position, curAgent.getPosition());
		//System.out.println("distance = " + distance);
		//System.out.println("curAgent.speed = " + curAgent.speed);
		double speed = curAgent.speed * 1000;
		if((speed < 0.5 && distance1<1000) ||
		   (speed >= 0.5 && speed<1 && distance1<3000)	||
		   (speed >= 1 && speed<2 && distance1<5000) ||
		   (speed >= 2 && distance1<10000) ||
		   	curAgent.loudDoor && distance1<5000 ||
		   	curAgent.openWindow && distance1 < 10000
				) {
			//System.out.println("I hear a sound!! Yay");
			Point vector = findVectorPath(curAgent.getPosition());
			double angle = findAngle(vector);
			NormalDistribution normal = new NormalDistribution(angle, 10);
			double direction = normal.sample();
			if(direction == 0) {direction = 360;}
			return direction;
		}}}
		return 0;
		}
	
	public ArrayList<Point> checkVectorSight(Point seeVector, int seeLength, int seeLengthSentry, int seeLengthObjects) {
		//System.out.println("position x and Y: " + position.x + ", " + position.y);
		checkSight.clear();
		seenIntruders.clear();
		//u creates a vector in same direction with correct length
		double u = (seeLength)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		double w = (seeLengthSentry)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		double v = (seeLengthSentry)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		temppos.x = position.x;
		temppos.y = position.y;
		//System.out.println("U: " + u);

		//check for 6m regular vision (for intruders/agents)
		
		for(int i = 0; i<10; i++) {
			temppos.x += 0.1*(u*seeVector.x);
			temppos.y += 0.1*(u*seeVector.y);
			//System.out.println("posX and Y: " + temppos.x + ", " + temppos.y)
			// translate to squares
			newSquare = new Point((int)(temppos.x/1000),(int)(temppos.y/1000));
			if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y) {
				lastSquare = newSquare;
				//System.out.println("lastSquare: " + lastSquare.getX() + ", " + lastSquare.getY());
				if(lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
					checkSight.add(lastSquare);}
			}
		}
		//check for 18m on intruders
		for(int i = 0; i<10; i++) {
			temppos.x += 0.1*(w*seeVector.x);
			temppos.y += 0.1*(w*seeVector.y);

			newSquare = new Point((temppos.x/1000),(temppos.y/1000));
			if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y && lastSquare.x >=0 && lastSquare.y >=0) {
				lastSquare = newSquare;
//				System.out.println("lastSquare x is " + lastSquare.x + " lastSquare y is " + lastSquare.y);
				if (lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < 50 && lastSquare.y < 50){
					if (square.board[lastSquare.x][lastSquare.y] == 1) {
//						System.out.println("WOOP WOOP I SEE SENTRY AT " + lastSquare);
						if (lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
							checkSight.add(lastSquare);
						}
					}
				}
			}
		}
		//check for 10m all other objects
		for(int i = 0; i<10; i++) {
			temppos.x += 0.1*(v*seeVector.x);
			temppos.y += 0.1*(v*seeVector.y);

			newSquare = new Point((temppos.x/1000),(temppos.y/1000));
			if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y && lastSquare.x >=0 && lastSquare.y >=0) {
				lastSquare = newSquare;
				if (lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < 50 && lastSquare.y < 50){
					if (square.board[lastSquare.x][lastSquare.y] == 1 ||
							square.board[lastSquare.x][lastSquare.y] == 2 ||
							square.board[lastSquare.x][lastSquare.y] == 3 ||
							square.board[lastSquare.x][lastSquare.y] == 4 ||
							square.board[lastSquare.x][lastSquare.y] == 5 ||
							square.board[lastSquare.x][lastSquare.y] == 42 ||
							square.board[lastSquare.x][lastSquare.y] == 32
							)
					{
						if (lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
							checkSight.add(lastSquare);
						}
					}
				}
			}
		}
		
		for(int i = 0; i<MainApp.getI().size(); i++) {
			for(int j = 0; j<checkSight.size(); j++) {
				if(MainApp.getI().get(i).getAgent().getCoordinates().equals(checkSight.get(j))) {
					seenIntruders.add(MainApp.getI().get(i).getAgent().getPosition());
				}
			}
		}
		return checkSight;
	}

	public void inShade(){
		shade = true;
	}

	public void enterTower(){}

	public void leaveTower(){}

	public void changeSpeed(double newSpeed){
		if(newSpeed <= BASESPEED){
			speed = newSpeed;
		}
	}
	

	
//	public double hear(ArrayList<Bot> bots) {
//		for(int i = 0; i<bots.size(); i++) {
//			if(bots.get(i).getAgent() != this) {
//			Agent curAgent = bots.get(i).getAgent();
//		distance = Math.sqrt(Math.pow((position.x + curAgent.getPosition().x), 2) + Math.pow((position.y + curAgent.getPosition().y), 2));
//		if((curAgent.speed < 0.5 && distance<1000) ||
//		   (curAgent.speed >= 0.5 && curAgent.speed<1 && distance<3000)	||
//		   (curAgent.speed >= 1 && curAgent.speed<2 && distance<5000) ||
//		   (curAgent.speed >= 2 && distance<10000)
//				) {
//			Point vector = new Point(position.x - curAgent.getPosition().x, position.y - curAgent.getPosition().y);
//			double angle = findAngle(vector);
//			NormalDistribution normal = new NormalDistribution(angle, 10);
//			double direction = normal.sample();
//			if(direction == 0) {direction = 360;}
//			return direction;
//		}}}
//		return 0;
//		}
	
	public void openDoor(boolean quiet) {
		openDoor = true;
		if(quiet) {
			NormalDistribution normal = new NormalDistribution(12, 2);
			doorTime = normal.sample();
		}
		else {
			loudDoor = true;
			doorTime = 5;
		}
		
	}

	public double findAngle(Point vector) {
		double tempAngle = 0;
		if(vector.x>0 && vector.y >=0)
			tempAngle = (180/Math.PI)*Math.atan(vector.y/vector.x) + 270;
		if(vector.x<0 && vector.y >=0)
			tempAngle = 180 - (180/Math.PI)*Math.atan(vector.y/(-vector.x));
		if(vector.x>0 && vector.y <0)
			tempAngle = 90 - (180/Math.PI)*Math.atan(-vector.y/vector.x);
		if(vector.x<0 && vector.y<0)
			tempAngle = 180 + (180/Math.PI)*Math.atan(vector.y/vector.x);
		if(vector.x==0 && vector.y<0)
			tempAngle = 90;
		if(vector.x==0 && vector.y>0)
			tempAngle = 270;
	return tempAngle;
	}

	public Point findVector(double angle) {
		Point tempVector = new Point();
		//System.out.println("find vector");
		//System.out.println("angle" + angle);
		if(angle< 90 && angle > 0) {
			tempVector.y = (int) (-Math.tan((Math.PI*angle)/180)*10000);
			tempVector.x = 10000;
		}
		if(angle == 90) {
			tempVector.y = 1;
			tempVector.x = 0;
		}
		if(angle>90 && angle <= 180) {
			tempVector.y= (int) (-Math.tan(Math.PI-(Math.PI*angle)/180)*10000);
			tempVector.x = -10000;
		}
		if(angle > 180 && angle < 270) {
			tempVector.y = (int) (-Math.tan(Math.PI*(2 - angle/180))*10000);
			tempVector.x = -10000;
		}
		if(angle == 270) {
			tempVector.y = -1;
			tempVector.x = 0;
		}
		if(angle>270 && angle < 360) {
			tempVector.y= (int) (-Math.tan(Math.PI*(1+ (angle/180)))*10000);
			tempVector.x = 10000;
		}
		//System.out.println("vector" + tempVector.x + " " + tempVector.y);
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
	
	public void setPosition(int x , int y) {
		this.position.x = x;
		this.position.y = y;
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
		double u = ((1000*BASESPEED)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		direction.x = (int) (position.x + Math.round(1000*(u*vector.x)));
		direction.y = (int) (position.y + Math.round(1000*(u*vector.y)));
		return direction;
	}
	
	
}
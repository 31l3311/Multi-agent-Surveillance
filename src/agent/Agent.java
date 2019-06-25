package agent;

import java.awt.Point;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;

import board.*;

import org.apache.commons.math3.*;

/**
 * This an abstract class of surveillance agents and intruders
 */
public abstract class Agent{
	
	
	public double angle;

	protected double newAngle;
	protected Point vector;
	public final double BASESPEED = 0.0014;
	public Point position;
	private Point lastSquare = new Point(0,0);
	private ArrayList<Point> checkSight = new ArrayList<Point>();
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
	protected boolean fastTurn;
	private double distance1;


	public abstract void move(int time);
	public abstract ArrayList update();
	public abstract ArrayList update(double angle);
	public abstract ArrayList update(boolean stop, double newAngle);
	protected int turnCounter;

	/**
	 * slow turning of the agent (45 degrees per second), it can see and move while turning
	 * @param newAngle is a new angle the agents wats to have after finishing turning
	 * @return the new Point direction vector of an agent
	 */
	public Point movingTurn(double newAngle) {

		if(angle != newAngle) {
		if(angle <= 180) {
		if(newAngle>angle && newAngle<(angle+180)) {
			if(Math.abs(newAngle-angle) <= 0.045*time) {
				angle = gon(newAngle);}
			else {
				angle = gon(angle + 0.045*time);
			}
		}
		else {
			if(newAngle > angle) {
				newAngle = newAngle - 360;
			}
			if(Math.abs(angle-newAngle) <= 0.045*time) {
				angle = gon(newAngle);
			}
			else {
				angle = gon(angle - 0.045*time);
			}
		}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if((angle - newAngle) <= 0.045*time) {
					angle = gon(newAngle);
				}
				else {
					angle = gon(angle - 0.045*time);
				}
			}
			else {
				if(newAngle < angle) {
					newAngle = 360 + newAngle;}
				if(newAngle - angle <= 0.045*time) {
					angle = gon(newAngle);
				}
				else {

					angle = gon(angle + 0.045*time);
				}
			}
		}}
		vector = findVector(angle);
		return vector;
	}

	/**
	 * fast turn of the agent (180 degrees per second), it can't see for the turning time plus 0.5 second
	 * @param newAngle is a new angle the agents wats to have after finishing turning
	 * @return the new Point direction vector of an agent
	 */
	public Point fastTurn(double newAngle) {
		fastTurn = true;
		turnCounter = 0;
		if(angle <= 180) {
			if(newAngle>angle && newAngle<(angle+180)) {
				if(Math.abs(newAngle-angle) <= 0.18*time) {
					angle = newAngle;}
				else {
					angle = angle + 0.18*time;
				}
			}
			else {
				if(newAngle > angle) {
					newAngle = newAngle - 360;
				}
				if(Math.abs(angle-newAngle) <= 0.18*time) {
					angle = gon(newAngle);
				}
				else {
					angle = angle - 0.18*time;
				}
			}}
		//angle is bigger than 180
		else {
			if(newAngle<angle && newAngle>(angle-180)) {
				if((angle - newAngle) <= 0.18*time) {
					angle = newAngle;}
				else {
					angle = angle - 0.18*time;
				}
			}
			else {
				if(newAngle < angle) {
					newAngle = 360 + newAngle;}
				if(newAngle - angle <= 0.18*time) {
					angle = gon(newAngle);
				}
				else {
					angle = gon(angle + 0.18*time);
				}
			}
		}
		vector = findVector(angle);
		return vector;
	}

	/**
	 * changing the speed of turning
	 * @param turnSpeed a boolean, when it is true agent turns with fast speed
	 */
	public void changeTurnSpeed(boolean turnSpeed){
		fastTurn = turnSpeed;
	}

	/**
	 * checks if the agent hears any sounds
	 * @return the direction of the soind
	 */
	public double hear() {
		
		for(int i = 0; i<MainApp.bots.size(); i++) {
			if(!MainApp.bots.get(i).getAgent().equals(this)) {
				Agent curAgent = MainApp.bots.get(i).getAgent();
				distance1 = MainApp.bots.get(i).distance(position, curAgent.getPosition());
				double speed = curAgent.speed * 1000;
				if((speed < 0.5 && distance1<1000) ||
						(speed >= 0.5 && speed<1 && distance1<3000)	||
		   				(speed >= 1 && speed<2 && distance1<5000) ||
		   				(speed >= 2 && distance1<10000) ||
		   				curAgent.loudDoor && distance1<5000 ||
		   				curAgent.openWindow && distance1 < 10000) {
						Point vector = findVectorPath(curAgent.getPosition());
						double angle = findAngle(vector);
						NormalDistribution normal = new NormalDistribution(angle, 10);
						double direction = normal.sample();
						if(direction == 0) {direction = 360;}
						return direction;
				}
			}
		}
		//it shouldn't reach this return statement
		return 0;
		}

	/**
	 * Method that chacks what the agent can see
 	 * @param seeVector a vision vector of the agent, influenced by the vision angle
	 * @param seeLength the distance from which agent can see objects
	 * @param seeLengthSentry the distance from which you can see the sentry tower
	 * @return
	 */
	public ArrayList<Point> checkVectorSight(Point seeVector, int seeLength, int seeLengthSentry) {
		checkSight.clear();
		seenIntruders.clear();
		//u creates a vector in the same direction with correct length
		double u = (seeLength)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		double w = (seeLengthSentry)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		temppos.x = position.x;
		temppos.y = position.y;

		//check for 10m regular vision for objects
		for(int i = 0; i<seeLength/500; i++) {
			temppos.x += (int)((500.0/seeLength)*(u*seeVector.x));
			temppos.y += (int)((500.0/seeLength)*(u*seeVector.y));
			// translate to squares
			newSquare = new Point((int)(temppos.x/1000),(int)(temppos.y/1000));
			if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y) {
				lastSquare = newSquare;
				if(lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
					checkSight.add(lastSquare);
				}
			}
		}
		//checks sentry towers
		for(int i = 0; i<36; i++) {
			temppos.x += (int)(1.0/36)*(w*seeVector.x);
			temppos.y += (int)(1.0/36)*(w*seeVector.y);

			newSquare = new Point((temppos.x/1000),(temppos.y/1000));
			if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y && lastSquare.x >=0 && lastSquare.y >=0) {
				lastSquare = newSquare;
				if (lastSquare.x >= 0 && lastSquare.y >= 0 && lastSquare.x < size.x && lastSquare.y < size.y) {
					if (square.board[lastSquare.x][lastSquare.y] == 1) {
						checkSight.add(lastSquare);
						}
					}
				}
			}

		//checks if the agent can see other agents
		for(int i = 0; i<MainApp.getI().size(); i++) {
			for(int j = 0; j<checkSight.size(); j++) {
				if(MainApp.getI().get(i).getAgent().getCoordinates().equals(checkSight.get(j)) && checkSight.get(j).distance(getCoordinates())<=6) {
					seenIntruders.add(MainApp.getI().get(i).getAgent().getPosition());
				}
			}
		}
		return checkSight;
	}

	/**
	 * Changes boolean shade to true, when the agent is in shade
	 */
	public void inShade(){
		shade = true;
	}

	public void enterTower(){}

	public void leaveTower(){}

	/**
	 * changes speed ofthe agent,
	 * @param newSpeed
	 */
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

	/**
	 * opens the door
	 * @param quiet a boolean that decides whether the doors open quietly or loudly
	 */
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

	/**
	 * Finds the angle from a given vector
	 * @param vector a Point vector
	 * @return an angle
	 */
	public double findAngle(Point vector) {
		double tempAngle = 0;
		if(vector.x>0 && vector.y >=0){
			tempAngle = 360-(180/Math.PI)*Math.atan(vector.y/vector.x);
			}
		if(vector.x<0 && vector.y >=0){
			tempAngle = (180 + (180/Math.PI)*Math.atan(vector.y/(-vector.x)));
			}
		if(vector.x>0 && vector.y <0){
			tempAngle = (180/Math.PI)*Math.atan(-vector.y/vector.x);
			}
		if(vector.x<0 && vector.y<0){
			tempAngle = 180 - (180/Math.PI)*Math.atan(vector.y/vector.x);
			}
		if(vector.x==0 && vector.y<0){
			tempAngle = 90;}
		if(vector.x==0 && vector.y>0){
			tempAngle = 270;}

	return gon(tempAngle);
	}

	/**
	 * finds a vector from a given angle
	 * @param angle is an integer angle
	 * @return a Point vector
	 */
	public Point findVector(double angle) {
		Point tempVector = new Point();
		////////System.out.println("find vector");
		////////System.out.println("angle" + angle);
		if(angle< 90 && angle >= 0) {
			tempVector.y = (int) (-Math.tan((Math.PI*angle)/180)*10000);
			tempVector.x = 10000;
		}
		if(angle == 90) {
			tempVector.y = -1;
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
			tempVector.y = 1;
			tempVector.x = 0;
		}
		if(angle>270 && angle < 360) {
			tempVector.y= (int) (-Math.tan(Math.PI*(1+ (angle/180)))*10000);
			tempVector.x = 10000;
		}
	return tempVector;
	}


	/**
	 * method which normalizes angles to values btwn 0 and 360
	 * @param angle an integer angle
	 * @return a normalized angle
	 */
	public double gon(double angle) {
		if(angle>=360)
			angle = angle - 360;
		if(angle<0)
			angle = angle + 360;
	return angle;
	}

	/**
	 * finds a direction vector to a chosen position
	 * @param targetPos a Point target position, where the agent wants to go
	 * @return a direction vector
	 */
	public Point findVectorPath(Point targetPos){
		return new Point(targetPos.x - this.getCoordinates().x, targetPos.y - this.getCoordinates().y);
	}

	/**
	 * sets the positioin of an agent
	 * @param x
	 * @param y
	 */
	public void setPosition(int x , int y) {
		this.position.x = x;
		this.position.y = y;
	}

	/**
	 * gets position of an agent in millimeters
	 * @return the position of the agent
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * gets the coordinates of the agent
	 * @return the coordinates of an agent
	 */
	public Point getCoordinates() {
		return new Point((int)(position.x/1000),(int)(position.y/1000));
	}

	/**
	 * gets the angle of the agent
	 * @return the angle of the agent
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * gets direction of type Point
	 * @return the direction of an agent
	 */
	public Point direction() {
		double u = ((1000*BASESPEED)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		direction.x = (int) (position.x + Math.round(1000*(u*vector.x)));
		direction.y = (int) (position.y + Math.round(1000*(u*vector.y)));
		return direction;
	}
}
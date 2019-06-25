package agent;

import java.awt.Point;
import java.util.ArrayList;

import board.MainApp;

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
	private int xPositionAgent;
	private int yPositionAgent;
	private int lookCounter = 40;
	private int movingCounter = 10;
	//visual range 7.5 m

	/**
	 * This is the constructor of the Intruder class, it has the following three parameter values
	 * @param position of type Point
	 * @param time of primitive object integer
	 * @param size of type Point
	 */
	public Intruder(Point position, int time, Point size) {
		speed = BASESPEED;
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
		this.size = size;
	}

	/**
	 * This method is intended to update the position, movement and graphical representation of the Intruder
	 * @return ArrayList of position coordinates
	 */
	public ArrayList update() {
		if (movingCounter <= 9){
			vector = movingTurn(gon(angle + 150));
		}
		if(fastTurn){
			if(!stop){
				move(time);
			}
			if(angle != newAngle) {
				vector = fastTurn(newAngle);
			}
			else {
				turnCounter++;
			}
			if(turnCounter > 8){
				fastTurn = false;
			}
			return new ArrayList();
		}
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

	/**
	 * This method is intended to update the position, movement and graphical representation of the intruder
	 * @param newAngle of primitive type double
	 * @return ArrayList of position coordinates
	 */
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
			if(fastTurn){
				vector = fastTurn(newAngle);
				return new ArrayList();
			}
			this.newAngle = gon(newAngle);
			vector = movingTurn(this.newAngle);}
		}
		return look();
	}

	/**
	 * This method is intended to change the mode of the intruder from walking to sprinting
	 * @param s of primitive type boolean to determine if sprinting or not
	 */
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

	/**
	 * This method is intended for the intruder to look around in it's limited field of view and update the ArrayList with the squares it has seen
	 * @return ArrayList of object SeenSquares
     */
	public ArrayList look() {
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		myDirection = checkVectorSight(vector, seeLength, seeLengthSentry);
		seenSquares.addAll(checkVectorSight(vector, seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength, seeLengthSentry));

		if ((lookCounter >= 40)) {
			for (int i = 0; i < (MainApp.amountSA); i++) {
				xPositionAgent = (int) (MainApp.surveillanceAgents.get(i).getCenterX() / 20);
				yPositionAgent = (int) (MainApp.surveillanceAgents.get(i).getCenterY() / 20);
				for (int v = 0; v < seenSquares.size(); v++) {
					if ((seenSquares.get(v).x == xPositionAgent) && (seenSquares.get(v).y == yPositionAgent)) {
						lookCounter = 0;
						movingCounter = 9;
					}
				}
			}
		}
		else {
			lookCounter++;
			movingCounter++;
		}
		return seenSquares;
	}

	/**
	 * Method intended to make the agent move
	 * @param time of primitive type integer
	 */
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
		////System.out.println("Position in move method:" + position);
	}

	/**
	 * Method intended to update the ArrayList of squares it has already seen
	 * @param stop of primitive type boolean
	 * @param newAngle of primitive type double
	 * @return another method call to the look method above, which will return an ArrayList with objects seensquares
	 */
	@Override
	public ArrayList update(boolean stop, double newAngle) {
		// TODO Auto-generated method stub
		this.stop = stop;
		vector = movingTurn(gon(newAngle));
		return look();
	}
}
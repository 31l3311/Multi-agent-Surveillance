package agent;

import java.awt.Point;
import java.util.ArrayList;

public class SurveillanceAgent extends Agent{
	
	//speed per millisecond
	private int seeLength = 6000;
	private int seeLengthSentry = 18000;
	private int seeLengthObjects = 10000;
	private boolean stop;
	private int timeTower;

	//defined as coordinates x,y in millimeters
	//looking vectors
	//visual range 6 m
	private double tempAngle = 0;
	private Point tempVector = new Point();
	private ArrayList info = new ArrayList();
	private int counter;

	public SurveillanceAgent(Point position, int time, Point size) {
		speed = BASESPEED;
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
		this.size = size;
	}
	
	public ArrayList update() {
	    if(shade){
	        move(time);
            if(angle != newAngle) {
                vector = movingTurn(newAngle);
            }
	        return lookShade();
        }
	    if(enterTower == true){
	        timeTower++;
	        if(timeTower >= 50){
	            entered = true;
	            enterTower = false;
	            enterTower();
	            return lookTower();
            }
            else{
	            return new ArrayList();
            }
        }
        if(entered == true){
	        enterTower();
	        /* to leave the tower after 12 seconds
	        timeTower++;
	        if(timeTower > 200){
	            leaveTower();
            }*/
	        return lookTower();
        }
        if(leaveTower == true){
            timeTower++;
            if(timeTower >= 50){
                leaveTower = false;
                stop = false;
            }
            else{
                return new ArrayList();
            }
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

	public ArrayList update(double newA) {
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
		this.newAngle = gon(newA);
		////System.out.println("New angle: " + newAngle);
		vector = movingTurn(newAngle);
		////System.out.println("New vector: " + vector.x + ", " + vector.y);
		}
		return look();
	}
	
	public ArrayList update(boolean stop, double newAngle) {
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
		this.stop = stop;
		vector = movingTurn(gon(newAngle));
		}
		return look();
	}

	public ArrayList look() {
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		////System.out.println("Position in sur agent: " + position.x + ", " + position.y);
		myDirection = checkVectorSight(vector, seeLength, seeLengthSentry, seeLengthObjects);
		seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(vector, seeLength, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength, seeLengthSentry, seeLengthObjects));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength, seeLengthSentry, seeLengthObjects));
        return seenSquares;
	}

    public ArrayList lookShade() {
        System.runFinalization();
        seenSquares.clear();
        myDirection.clear();
        ////System.out.println("Position in sur agent: " + position.x + ", " + position.y);
        myDirection = checkVectorSight(vector, 3000, seeLengthSentry, seeLengthObjects);
        seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength/2, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength/2, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength/2, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength/2, seeLengthSentry, seeLengthObjects));
        return seenSquares;
    }

    public ArrayList lookTower() {
        System.runFinalization();
        seenSquares.clear();
        myDirection.clear();
        myDirection = checkVectorSight(vector, 15000, seeLengthSentry, seeLengthObjects);
        seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 7.5)), 15000, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 15)), 15000, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 7.5)), 15000, seeLengthSentry, seeLengthObjects));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 15)), 15000, seeLengthSentry, seeLengthObjects));
        myDirection = checkVectorSight(vector, 2000, seeLengthSentry, seeLengthObjects);
        seenSquares.removeAll(myDirection);
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle + 7.5)), 2000, seeLengthSentry, seeLengthObjects));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle + 15)), 2000, seeLengthSentry, seeLengthObjects));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle - 7.5)), 2000, seeLengthSentry, seeLengthObjects));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle - 15)), 2000, seeLengthSentry, seeLengthObjects));
        return seenSquares;
    }

    @Override
	public void enterTower() {
	    if(enterTower == false && entered == false) {
            enterTower = true;
            stop = true;
            timeTower = 0;
        }
        else if(entered == true){
            ////System.out.println("angle" + angle);
            vector = movingTurn(gon(angle + 2.7));
            //timeTower = 0;
            ////System.out.println("new angle" + angle);

        }
		//delay three seconds
	}

	public void leaveTower(){
	        ////System.out.println("leave tower");
            leaveTower = true;
            enterTower = false;
            entered = false;
            timeTower = 0;
    }

	@Override
	public void move(int time) {
		////System.out.println("Current Position: " + position.x + ", " + position.y);
		double u = ((time*speed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		////System.out.println("Updated Position: " + position.x + ", " + position.y);
	}

	public int[] getTranslation() {

		int[] translation = new int[2];
		int actual_x = (int) getPosition().getX();
		int actual_y = (int) getPosition().getY();

		int locX = ( (int) (getPosition().getX())/1000);
		int locY = ( (int) (getPosition().getY())/1000);

		//calculation of pixels to square

		int closest_x = (locX - 1) * 20 * 50;
		int closest_y = (locY - 1) * 20 * 50;

		int x_in_pix = Math.abs(closest_x - actual_x);
		int y_in_pix = Math.abs(closest_y - actual_y);

		translation[0] = x_in_pix;
		translation[1] = y_in_pix;
		////System.out.println("GETS HERE 2!");
		
		return translation;
	}
}
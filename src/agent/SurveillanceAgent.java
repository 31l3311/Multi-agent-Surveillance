package agent;

import java.awt.Point;
import java.util.ArrayList;

import board.MainApp;


public class SurveillanceAgent extends Agent{

	//speed per millisecond
	private int seeLength = 10000;
	private int seeLengthSentry = 18000;
	private int seeLengthObjects = 10000;
	public boolean stop;
	private int timeTower;

	//defined as coordinates x,y in millimeters
	//looking vectors
	//visual range 6 m
	private double tempAngle = 0;
	private Point tempVector = new Point();
	private ArrayList info = new ArrayList();
	private int counter;
	private boolean leftTower;

	public SurveillanceAgent(Point position, int time, Point size) {
		speed = BASESPEED;
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
		this.size = size;
	}

	/**
	 * updates the surveillance agent, checks for fast turns, entering/leaving tower, going trough doors and shades. changes the angle if not already done
	 * @return method look, which returns the seen squares for the bot in that moment, see method look
	 */
	public ArrayList update() {
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
	        // to leave the tower after 8 seconds
	        timeTower++;
	        if(timeTower > 240){
	            leaveTower();
            }
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
        if(MainApp.board[getCoordinates().x][getCoordinates().y] == 1 && leftTower == false ) {
			enterTower();
		}
		if(shade){
			move(time);
			////System.out.println(1);
			if(angle != newAngle) {
				vector = movingTurn(newAngle);
			}
			return lookShade();
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
			move(time);
			////System.out.println(2);
		}

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
	 * same as other update method, but checking less things, this gets called when a new angle is given wtih calling update
	 * @param newA is the new angle given when update is called
	 * @return method look, which returns the seen squares for the bot in that moment, see method look
	 */
	public ArrayList update(double newA) {
		//System.out.println("UPDATE WALK CALLED");
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
			if (stop == false) {
				////System.out.println(1);

				move(time);
		}
			if(MainApp.board[getCoordinates().x][getCoordinates().y] == 1 && leftTower== false) {
				enterTower();
			}
		//System.out.println("New angle before gon: " + newA);
		this.newAngle = gon(newA);
		//System.out.println("New angle after gon: " + newAngle);
		if(fastTurn){
			vector = fastTurn(newAngle);
			return new ArrayList();
		}
		////System.out.println("New angle: " + newAngle);

		vector = movingTurn(newAngle);
		//////System.out.println("New vector: " + vector.x + ", " + vector.y);
		}
		return look();
	}

	/**
	 * same as other update, but with boolean stop for stopping the bot
	 * @param stop set bot to stop or let bot go again with false
	 * @param newAngle new angle given to bot
	 * @return method look, which returns the seen squares for the bot in that moment, see method look
	 *          */
	public ArrayList update(boolean stop, double newAngle) {
		//System.out.println("UPDATE STOP CALLED");
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
		if(MainApp.board[getCoordinates().x][getCoordinates().y] == 1 && leaveTower== false) {
			enterTower();
		}
		if(openDoor == false && openWindow == false) {
		//this.stop = stop;
		if(fastTurn){
			vector = fastTurn(gon(newAngle));
			return new ArrayList();
		}
		this.newAngle = gon(newAngle);
		vector = movingTurn(gon(newAngle));
		}
		return look();
	}

	/**
	 * set boolean for leaving tower and not immediatly entering it again
	 * @param hi true if bot just left tower
	 */
	public void setLeftTower(boolean hi) {
		leftTower = hi;
	}

	/**
	 * looks for al 5 vectors of bot, checks the squares it can see and adds them to al list, with the right value of the map
	 * @return arraylist of the seensquares
	 */
	public ArrayList look() {
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		//////System.out.println("Position in sur agent: " + position.x + ", " + position.y);
		myDirection = checkVectorSight(vector, seeLength, seeLengthSentry);
		seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(vector, seeLength, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength, seeLengthSentry));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength, seeLengthSentry));
        return seenSquares;
	}

	/**
	 * same as first look, but with the sight in square, so divided by 2
	 * @return arraylist of the seensquares
	 */
    public ArrayList lookShade() {
        System.runFinalization();
        seenSquares.clear();
        myDirection.clear();
        //////System.out.println("Position in sur agent: " + position.x + ", " + position.y);
        myDirection = checkVectorSight(vector, seeLength/2, seeLengthSentry);
        seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength/2, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength/2, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength/2, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength/2, seeLengthSentry));
        return seenSquares;
    }

	/**
	 * same as first look, but this time adding for 15 meter the sight and deleting squares seen within 2 meters
	 * @return
	 */
    public ArrayList lookTower() {
        System.runFinalization();
        seenSquares.clear();
        myDirection.clear();
        myDirection = checkVectorSight(vector, 15000, seeLengthSentry);
        seenSquares.addAll(myDirection);
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 7.5)), 15000, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle + 15)), 15000, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 7.5)), 15000, seeLengthSentry));
        seenSquares.addAll(checkVectorSight(findVector(gon(angle - 15)), 15000, seeLengthSentry));
        //myDirection = checkVectorSight(vector, 2000, seeLengthSentry, seeLengthObjects);
        //seenSquares.removeAll(myDirection);
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle)), 2000, seeLengthSentry));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle + 7.5)), 2000, seeLengthSentry));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle + 15)), 2000, seeLengthSentry));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle - 7.5)), 2000, seeLengthSentry));
        seenSquares.removeAll(checkVectorSight(findVector(gon(angle - 15)), 2000, seeLengthSentry));
        return seenSquares;
    }

	/**
	 * if not entered yet, wait the 3 seconds otherwise start turning and looking inside sentrytower
	 */
    @Override
	public void enterTower() {
	    if(enterTower == false && entered == false) {
            enterTower = true;
            stop = true;
            timeTower = 0;
            position = new Point(getCoordinates().x*1000 + 500, getCoordinates().y*1000 + 500);
        }
        else if(entered == true){
            ////System.out.println("angle " + angle);
            vector = movingTurn(gon(angle + 0.045*time));
            //timeTower = 0;
            ////System.out.println("new angle " + angle);

        }
		//delay three seconds
	}

	/**
	 * leave the tower, update() does the rest
	 */
	public void leaveTower(){
	        //////System.out.println("leave tower");
            leaveTower = true;
            enterTower = false;
            entered = false;
            timeTower = 0;
            leftTower = true;
    }

	/**
	 * moves the bot itself with a speed and vectors
	 * @param time with a given parameter time, given through constructer surveillance bot
	 */
	@Override
	public void move(int time) {
		//System.out.println("move");
		////System.out.println("Current Position: " + position.x + ", " + position.y);
		double u = ((time*speed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		//System.out.println("Updated Position: " + position.x + ", " + position.y);
	}

	/**
	 * changes pixels to squares
	 * @return the translation 
	 */
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

		return translation;
	}
}

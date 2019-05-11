package agent;



import java.awt.Point;
import java.util.ArrayList;

public class SurveillanceAgent extends Agent{
	
	//speed per millisecond
	public double speed;
	private int seeLength = 6000;
	private boolean stop;
	
	//defined as coordinates x,y in millimeters
	//looking vectors
	

	//visual range 6 m
	
	private double tempAngle = 0;
	private Point tempVector = new Point();
	private ArrayList info = new ArrayList();

	public SurveillanceAgent(Point position, int time, Point size) {
		this.position = position;
		vector = new Point(1, 1);
		angle = findAngle(vector);
		seenSquares = new ArrayList<Point>();
		this.time = time;
		this.size = size;
	}
	
	public ArrayList update() {
		if(stop == false) {
		move(time);}
		if(angle != newAngle) {
			vector = movingTurn(newAngle);
		}
		else {
			stop = false;
		}
		return look();

	}

	public ArrayList update(double newA) {
		move(time);
		this.newAngle = gon(newA);
		vector = movingTurn(newAngle);
		return look();
	}
	
	public ArrayList update(boolean stop, double newAngle) {
		this.stop = stop;
		vector = movingTurn(gon(newAngle));
		return look();
	}

	public ArrayList look() {
		System.runFinalization();
		seenSquares.clear();
		myDirection.clear();
		//System.out.println("Position in sur agent: " + position.x + ", " + position.y);
		myDirection = checkVectorSight(vector, seeLength);
		seenSquares.addAll(myDirection);
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle + 22.5)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 11.25)), seeLength));
		seenSquares.addAll(checkVectorSight(findVector(gon(angle - 22.5)), seeLength));
		return seenSquares;
	}

	public void enterTower() {
		//delay three seconds
		
		
		
	}

	@Override
	public void move(int time) {
		//System.out.println("Current Position: " + position.x + ", " + position.y);
		double u = ((time*baseSpeed)/Math.sqrt(Math.pow( vector.x, 2) + Math.pow( vector.y, 2)));
		position.x += Math.round(1000*(u*vector.x));
		position.y += Math.round(1000*(u*vector.y));
		//System.out.println("Updated Position: " + position.x + ", " + position.y);
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
		//System.out.println("GETS HERE 2!");
		
		return translation;
	}
}
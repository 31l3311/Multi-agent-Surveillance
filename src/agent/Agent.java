import java.awt.Point;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;

public abstract class Agent{
	
	public double angle;
	public Point position;
	private Point lastSquare = new Point(0,0);
	private ArrayList<Point> seenSquares = new ArrayList<Point>();
	private double distance;
	
	public abstract void move(int time);
	//public abstract void turn(int angle);
	
	public Point movingTurn(double newAngle, int time, Point vector) {
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
		return vector;
	}
	
	public ArrayList<Point> checkVectorSight(Point seeVector, int seeLength, Point position) {
		seenSquares.clear();
		double u = (seeLength*0.1)/(Math.sqrt(Math.pow(seeVector.x, 2) + Math.pow(seeVector.y, 2)));
		System.out.println("U: " + u);
		for(int i = 0; i<10; i++) {
			position.x += (u*seeVector.x);
			position.y += (u*seeVector.y);
		//System.out.println("posX and Y: " + posX + ", " + posY);
		Point newSquare = new Point((int)(position.x/1000),(int)(position.y/1000));
		if(newSquare.x != lastSquare.x || newSquare.y != lastSquare.y) {
			lastSquare = newSquare;
			//System.out.println("lastSquare: " + lastSquare.getX() + ", " + lastSquare.getY());
			seenSquares.add(lastSquare);
		}}
		return seenSquares;
	}
	
	public void hear(ArrayList<SurveillanceAgent> agents, Point position) {
		for(int i = 0; i<agents.size(); i++) {
		distance = Math.sqrt(Math.pow((position.x + agents.get(i).getPosition().x), 2) + Math.pow((position.y + agents.get(i).getPosition().y), 2));
		if((agents.get(i).speed < 0.5 && distance<1000) ||
		   (agents.get(i).speed >= 0.5 && agents.get(i).speed<1 && distance<3000)	||
		   (agents.get(i).speed >= 1 && agents.get(i).speed<2 && distance<5000) ||
		   (agents.get(i).speed >= 2 && distance<10000)
				) {
			Point vector = new Point(position.x - agents.get(i).getPosition().x, position.y - agents.get(i).getPosition().y);
			double angle = findAngle(vector);
			NormalDistribution normal = new NormalDistribution(angle, 10);
			double direction = normal.sample();
		}
	}}
	
	public double findAngle(Point vector) {
		double tempAngle = 0;
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
		Point tempVector = new Point();
		if(angle< 90) {
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
	
	public Point getPosition() {
		return position;
	}
	
}
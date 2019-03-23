package agent;



import java.awt.Point;
import java.util.ArrayList;

public class RandomBot{
	public Agent agent;
	private boolean surveillance;
	private int[][] map;
	private int counter = 0;
	
	public RandomBot(boolean surveillance, Point position, int time, Point size) {
		this.surveillance = surveillance;
		map = new int[size.x][size.y];
		if(surveillance) {
			agent = new SurveillanceAgent(position, time);
		}
		else {
			agent = new Intruder(position, time);
		}
	}
	
	public ArrayList update() {
		if(counter >= 100 || avoidObjects()) {
			counter = 0;
			return agent.update(changeAngle());
		}
		counter++;
		return agent.update();
	}
	
	public double changeAngle() {
		return (360*Math.random());
	}
	
	public void updateMap(Point loc, int i) {
		map[loc.x][loc.y] = i;
		if(i == 1) {
			// implement method to avoid tree or shit
			System.out.println("I see a tree!");
		}
	}
	
	public boolean avoidObjects() {
		for( int i = 0; i < agent.myDirection.size(); i++) {
			if (map[agent.myDirection.get(i).x][agent.myDirection.get(i).y] != 0) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
}
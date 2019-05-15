package Bots;



import java.awt.Point;
import java.util.ArrayList;

import agent.Agent;
import agent.Intruder;
import agent.SurveillanceAgent;

public class RandomBot extends Bot{
	public Agent agent;
	private boolean surveillance;
	private int[][] map;
	private int counter = 0;
	private ArrayList bots;
	
	public RandomBot(boolean surveillance, Point position, int time, Point size){
		this.surveillance = surveillance;
		map = new int[size.x][size.y];
		if(surveillance) {
			agent = new SurveillanceAgent(position, time, size);
		}
		else {
			agent = new Intruder(position, time, size);
		}
	}
	
	public void setBots(ArrayList bots) {
		this.bots = bots;
	}
	
	public ArrayList<Bot> getBots(){
		return bots;
	}
	
	public ArrayList update() {
		if(agent.myDirection.size()>2) {
		if(map[agent.myDirection.get(2).x][agent.myDirection.get(2).y] != 0) {
			System.out.println("I should turn around now!");
			return agent.update(true,(agent.getAngle() + 160));
		}}
		if(counter >= 100 || avoidObjects()) {
			counter = 0;
			return agent.update(changeAngle());
		}
		counter++;
		return agent.update();
	}
	
	public double changeAngle() {
		double angle = (360*Math.random());
		//System.out.println("New angle = " + angle);
		return angle;
	}
	
	public void updateMap(Point loc, int i) {
		map[loc.x][loc.y] = i;
		if(i == 1) {
			// implement method to avoid tree or shit
			//System.out.println("I see a tree!");
		}
	}
	
	public void updateAgent() {
		agent.update();
		agent.hear(getBots());
	}
	
	public boolean avoidObjects() {
		for( int i = 0; i < agent.myDirection.size(); i++) {
			if (map[agent.myDirection.get(i).x][agent.myDirection.get(i).y] != 0) {
				System.out.println("I see something at : " + agent.myDirection.get(i).x + ", " + agent.myDirection.get(i).y);
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
}
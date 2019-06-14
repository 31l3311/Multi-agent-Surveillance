package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.Agent;




public abstract class Bot{
	
	
	protected int[][] map;
	private ArrayList bots;
	protected Agent agent;
	public Point position;
	
	public abstract void updateMap(Point loc, int i);
	public abstract ArrayList update();
	public abstract Agent getAgent();
	public abstract void setSounds(double direction);
	
	public void checkLocation() {
		//System.out.println("agent: " + agent);
		//System.out.println("X:" + agent.getCoordinates().x);
		//System.out.println("Y: " + agent.getCoordinates().y);
		int obstacle = map[agent.getCoordinates().x][agent.getCoordinates().y];
		if(obstacle == 4 || obstacle == 42) {
			agent.openDoor(true);
		}
		if(obstacle == 3 || obstacle == 32) {
			agent.openWindow = true;
		}
	}
	
	public double distance(Point start, Point end) {
		return Math.sqrt(Math.pow((start.x-end.x), 2) + Math.pow((start.y-end.y), 2));
	}
	
	
}
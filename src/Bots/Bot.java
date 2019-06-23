package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.*;
import board.MainApp;


public abstract class Bot{
	
	
	protected int[][] map =  MainApp.board;
	private ArrayList bots;
	protected Agent agent;
	protected boolean pursuit;
	public Point position;
	public Point pursuitGoal;
	public boolean explorationComplete;
	
	public abstract void updateMap(Point loc, int i);
	public abstract ArrayList update();
	public abstract Agent getAgent();
	public abstract void setSounds(double direction);
	
	public void checkLocation(boolean bounceOfWalls) {
	if(bounceOfWalls) {
		try {
			////System.out.println("agent: " + agent);
			////System.out.println("X:" + agent.getCoordinates().x);
			////System.out.println("Y: " + agent.getCoordinates().y);
			if (agent.getCoordinates().x > 0 && agent.getCoordinates().y > 0) {
				int obstacle = map[agent.getCoordinates().x][agent.getCoordinates().y];

				if (obstacle == 4 || obstacle == 42) {
					agent.openDoor(true);
				}
				if (obstacle == 3 || obstacle == 32) {
					agent.openWindow = true;
				}
			} else if (agent.getCoordinates().x < 1) {
				agent.setPosition(agent.getPosition().x + 2000, agent.getPosition().y);
			} else if (agent.getCoordinates().y < 1) {
				agent.setPosition(agent.getPosition().x, agent.getPosition().y + 2000);
			} else if (agent.getCoordinates().x > 49) {
				agent.setPosition(agent.getPosition().x - 2000, agent.getPosition().y);
			} else if (agent.getCoordinates().y > 49) {
				agent.setPosition(agent.getPosition().x, agent.getPosition().y - 2000);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	}
	
	public double distance(Point start, Point end) {
		return Math.sqrt(Math.pow((start.x-end.x), 2) + Math.pow((start.y-end.y), 2));
	}
	
	public void setPursuit(boolean k) {
		pursuit = k;
	}
	
	
}
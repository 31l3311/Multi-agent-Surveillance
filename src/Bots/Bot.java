package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.*;
import board.MainApp;


public abstract class Bot{

	/**
	 * This is a double array which records all the object positions on the map. This double array is of type protected, which means that the classes implementing this abstract class won't be able to change it.
	 */
	protected int[][] map =  MainApp.board;

	private ArrayList bots;
	protected Agent agent;
	protected boolean pursuit;
	public Point position;
	public Point pursuitGoal;
	public boolean explorationComplete;

	/**
	 * Abstract method which is meant to update the map and will be extended by the class which implements the abstract bot class
	 * @param loc of type Point
	 * @param i of primitive type integer
	 */
	public abstract void updateMap(Point loc, int i);

	/**
	 * Abstract method which is meant to update the position of the bot and will be extended by the class which implements the abstract bot class
	 * @return ArrayList
	 */
	public abstract ArrayList update();

	/**
	 * Abstract method which is meant to fetch the agent and will be extended by the class which implements the abstract bot class
	 * @return an object of type agent
	 */
	public abstract Agent getAgent();

	/**
	 * Abstract method which is meant to set the sound and will be extended by the class which implements the abstract bot class
	 * @param direction of primitive type double
	 */
	public abstract void setSounds(double direction);

	/**
	 * Method which is intended to check if the bot is near doors or walls
	 * @param bounceOfWalls of primitive type boolean.
	 */
	public void checkLocation(boolean bounceOfWalls) {
	if(bounceOfWalls) {
		try {
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

	/**
	 * Method intented to estimate the distance
	 * @param start of type Point
	 * @param end of type Point
	 * @return primitive type double which denotes the distance
	 */
	public double distance(Point start, Point end) {
		return Math.sqrt(Math.pow((start.x-end.x), 2) + Math.pow((start.y-end.y), 2));
	}

	/**
	 * Method which determines if the pursuit should be initiated or not
	 * @param k of primitive type boolean
	 */
	public void setPursuit(boolean k) {
		pursuit = k;
	}
	
	
}
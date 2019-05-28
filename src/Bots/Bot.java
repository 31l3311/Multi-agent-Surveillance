
package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.Agent;



public abstract class Bot{
	
	
	private int[][] map;
	private ArrayList bots;
	
	public abstract void updateMap(Point loc, int i);
	public abstract ArrayList update();
	public abstract Agent getAgent();
	
	
	
	
}
package Bots;

import java.awt.Point;

public class Node {

	public Node parent;
	public Point position;
	public double f,g,h;
	private double pheromoneCount;
	private double factor = 0.01;
	
	public Node(Point position, double distance, boolean surveillance) {
		this.position = position;
		g = 0;
		h = distance;
		f = g+h;
	}
	
	public Node(Point position, Node parent, double distance, int pheromoneCount, int object) {
		this.pheromoneCount = pheromoneCount + parent.pheromoneCount;
		this.parent = parent;
		this.position = position;
		
			g = (Math.sqrt(Math.pow((position.x-parent.position.x), 2)+ Math.pow((position.y - parent.position.y), 2)) + parent.g) - factor*pheromoneCount;
		
		if(object == 3 || object == 32) {
			g = g + 3*1.4;
		}
		if(object == 4 || object == 42) {
			g = g + 10*1.4;
		}
		h = distance;
		f = g+h;
	}
	
	public Node(Point position, Node parent, double distance, int object) {
		this.pheromoneCount = pheromoneCount + parent.pheromoneCount;
		this.parent = parent;
		this.position = position;
		
		
		g = (Math.sqrt(Math.pow((position.x-parent.position.x), 2)+ Math.pow((position.y - parent.position.y), 2)) + parent.g);
		if(object == 3 || object == 32) {
			g = g + 3*1.4;
		}
		if(object == 4 || object == 42) {
			g = g + 10*1.4;
		}
		h = distance;
		f = g+h;
	}
	
}

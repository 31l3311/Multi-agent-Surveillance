package Bots;

import java.awt.Point;

public class Node {

	public Node parent;
	public Point position;
	public double f,g,h;
	public double pheromoneCount;
	private double factor = 0.001;

	/**
	 * Constructor for parent Node class. Includes distance formula f = g + h used for determining paths
	 * @param position Current position Point
	 * @param distance Distance to next point
	 * @param surveillance Boolean determining whether we are working with a surveillance bot or not
	 */
	public Node(Point position, double distance) {
		this.position = position;
		g = 0;
		h = distance;
		f = g+h;
	}

	/**
	 * Constructor for child Node class in surveillance. Includes formula for calculating factor g as well as the general formula f = g +h.
	 * g is dependent on pheromoneCount value.
	 * @param position Current position Point
	 * @param parent Parent of current node
	 * @param distance Distance to next point
	 * @param pheromoneCount Value of the pheromones on the map
	 * @param object Number corresponding to current object. 3 and 32 are doors. 4 and 42 are windows.
	 */
	public Node(Point position, Node parent, double distance, int pheromoneCount, int object) {
		this.pheromoneCount = pheromoneCount;
		this.parent = parent;
		this.position = position;
		
			g = Math.max(Math.sqrt(Math.pow((position.x-parent.position.x), 2)+ Math.pow((position.y - parent.position.y), 2)+ parent.g) - factor*pheromoneCount, 0) ;
		
		if(object == 3 || object == 32) {
			g = g + 3*1.4;
		}
		if(object == 4 || object == 42) {
			g = g + 10*1.4;
		}
		h = distance;
		f = g+h;
	}

	/**
	 * Constructor for child Node class during pursuit. Similar to other two constructors above.
	 * @param position Current position Point.
	 * @param parent Parent of current node
	 * @param distance Distance to next point
	 * @param object Number corresponding to current object. 3 and 32 are doors. 4 and 41 are windows.
	 */
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

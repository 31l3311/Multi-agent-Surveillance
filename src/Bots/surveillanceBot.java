package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.SurveillanceAgent;

public class surveillanceBot  extends Bot{
	
	private SurveillanceAgent agent;
	private int[][] map;
	private int[][] sectionMap;
	private int[][] pheromoneMap;
	private int max;
	private Point bestLoc;
	
	//walking
	int nextPathpos;
	
	//astar
	private ArrayList<Node> closedNodes;
	private ArrayList<Node> openNodes;
	private ArrayList<Point> path;
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private Point position;
	private boolean checked;
	
	
	
	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		agent = new SurveillanceAgent(topLeft, time, size );
		pheromoneMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(sectionMap[i][j]!= 0) 
					{pheromoneMap[i][j] = -1;}
			}
		}
		
		
	}
	
	public void update() {
		//update pheromonemap
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1) {
				pheromoneMap[i][j]+=1;}
			
		}}
		
		//update agent
		if(agent.getCoordinates() == path.get(0)) {
			aStar();
		}
		else if(agent.getCoordinates() == path.get(nextPathpos)) {
			nextPathpos -=1;
			agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
		}
		else {
			agent.update();
		}
		
		}
	
	public void surveil() {
		max = 0;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(pheromoneMap[i][j] >= max ) {
					max = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}
		}
		
		
	}
	
	public void aStar() {
		//f = g+h
		//reset values
		path.clear();
		openNodes.clear();
		closedNodes.clear();
		
		
		startPos = agent.getCoordinates();
		startNode = new Node(startPos, distance(startPos, bestLoc));
		openNodes.add(startNode);
		
		while(!openNodes.isEmpty()) {
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			
			if(bestNode.position == bestLoc) {
				findPath(bestNode);
				nextPathpos = path.size() - 1;
			}
			else {
				for(int i = -1; i<=1; i++) {
					for(int j = -1; j<=1; j++) {
						if(bestNode.position.x + i >= 0 && bestNode.position.y + j >= 0 ) {
							position = new Point(bestNode.position.x + i, bestNode.position.y + j);
							tempNode = new Node(position, bestNode, distance(position, bestLoc), pheromoneMap[position.x][position.y]);
							for(int k = 0; k< closedNodes.size(); k++) {
								if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
									if(tempNode.f< closedNodes.get(k).f) {
										openNodes.add(tempNode);
									}
									checked = true;
								}
							}
							if(checked == false) {
							for(int k = 0; k< openNodes.size() ; k++) {
								if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
									if(tempNode.f< openNodes.get(k).f) {
										openNodes.add(tempNode);
										openNodes.remove(k);
									}
									checked = true;
								}
							}}
							if(checked == false) {
								if(map[tempNode.position.x][tempNode.position.y] == 0){
									openNodes.add(tempNode);}}
						}
					}
				}
			
			}
		}
	}
	
	public void findPath(Node node){
		path.add(node.position);
		if(node != startNode) {
		findPath(node.parent); }
	}
	
	public double distance(Point start, Point end) {
		return Math.sqrt(Math.pow((start.x-end.x), 2) + Math.pow((start.y-end.y), 2));
	}
}
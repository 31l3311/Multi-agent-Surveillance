package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.SurveillanceAgent;

public class surveillanceBot  extends Bot{
	
	public SurveillanceAgent agent;
	private int[][] map;
	private int[][] sectionMap;
	private int[][] pheromoneMap;
	private int max;
	private Point bestLoc;
	
	private boolean explorationComplete;
	
	//walking
	int nextPathpos;
	
	//astar
	private ArrayList<Node> closedNodes= new ArrayList<Node>();
	private ArrayList<Node> openNodes= new ArrayList<Node>();
	private ArrayList<Point> path = new ArrayList<Point>();
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private Point position;
	private boolean checked;
	
	
	
	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		for(int i = 0; i<sectionMap.length; i++ ) {
			for(int j = 0; j<sectionMap[0].length; j++ ) {
					sectionMap[i][j] = -1;
			}
		}
		agent = new SurveillanceAgent(topLeft, time, size );
		System.out.println("agent: " + agent);
		pheromoneMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
//		for(int i = 0; i<pheromoneMap.length; i++ ) {
//			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
//				if(sectionMap[i][j]!= 0) 
//					{pheromoneMap[i][j] = -1;}
//			}
//		}
		
		
	}
	
	public void updateMap(Point loc, int i) {
		sectionMap[loc.x][loc.y] = i;
	}
	
	public ArrayList update() {
		//update pheromonemap
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1) {
				pheromoneMap[i][j]+=1;}
			
		}}
		
		//update agent
		if(!explorationComplete) {
			explore();
		}
		else if(agent.getCoordinates() == path.get(0)) {
			aStar(surveil(), true);
		}
		
		
		if(agent.getCoordinates().equals(path.get(nextPathpos))) {
			nextPathpos -=1;
			 return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
		}
		else {
			return agent.update();
		}
		
		}
	
	public Point surveil() {
		max = 0;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(pheromoneMap[i][j] >= max ) {
					max = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}
		}
		return bestLoc;
		
	}
	
	public void explore() {
		explorationComplete = true;
		outerloop:
		for(int i=0; i<sectionMap.length; i++) {
			for(int j=0; j<sectionMap[0].length; j++) {
				if(sectionMap[i][j]<0 && !(agent.getCoordinates().x==i && agent.getCoordinates().y==j)) {
					System.out.println("Next point:" + i +", " + j);
					explorationComplete = false;
					aStar(new Point(i,j), false);
					break outerloop;
				}
			}
		}
	}
	
	public void aStar(Point goal, boolean surveillance) {
		//f = g+h
		//reset values
		path.clear();
		openNodes.clear();
		closedNodes.clear();
		
		
		startPos = agent.getCoordinates();
		startNode = new Node(startPos, distance(startPos, goal), surveillance);
		openNodes.add(startNode);
		int counter = 0;
		while(!openNodes.isEmpty() && counter<10) {
		System.out.println("goal node" + goal.x +", " + goal.y);	
		System.out.println("Open nodes wl: " + openNodes.size());	
			bestNode = openNodes.get(0);
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			System.out.println("best node" + bestNode.position.x +", " + bestNode.position.y);	
			
			if(bestNode.position.x == goal.x && bestNode.position.y == goal.y ) {
				System.out.println("Path :");
				findPath(bestNode);
				nextPathpos = path.size() - 1;
				for(int i = 0; i<path.size(); i++) {
					System.out.println(path.get(i));
				}
				break;
			}
			else {
				for(int i = -1; i<=1; i++) {
					for(int j = -1; j<=1; j++) {
						if(bestNode.position.x + i >= 0 && bestNode.position.y + j >= 0 && bestNode.position.x + i < sectionMap.length && bestNode.position.y + j < sectionMap[0].length) {
						if(i!=0 || j!=0) {
							checked = false;
							position = new Point(bestNode.position.x + i, bestNode.position.y + j);
							tempNode = new Node(position, bestNode, distance(position, goal), pheromoneMap[position.x][position.y], surveillance);
							
							for(int k = 0; k< closedNodes.size(); k++) {
								if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
									if(tempNode.f< closedNodes.get(k).f) {
										openNodes.add(tempNode);
										System.out.println("add to open nodes 1");
									}
									checked = true;
								}
							}
							
							if(checked == false) {
							for(int k = 0; k< openNodes.size() ; k++) {
								if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
									if(tempNode.f< openNodes.get(k).f) {
										openNodes.add(tempNode);
										System.out.println("add to open nodes 2");
										openNodes.remove(k);
									}
									checked = true;
								}
							}}
							
							if(checked == false) {
								if(sectionMap[tempNode.position.x][tempNode.position.y] <= 0){
									openNodes.add(tempNode);
									System.out.println("add to open nodes 3");
								}}
						}}
					}
				}
			
			}
			counter++;
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
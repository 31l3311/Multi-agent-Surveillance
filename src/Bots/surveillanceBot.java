package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.SurveillanceAgent;
import board.MainApp;
import board.Square;

public class surveillanceBot  extends Bot{
	
	//map
	private int[][] sectionMap;
	private int[][] pheromoneMap;
	private Point topLeft, bottomRight;
	
	private ArrayList sounds = new ArrayList();
	//amount of agents which should go into pursuit, dependant on total amount of SA's
	private double pursuitAgent;
	private int min;
	private Point bestLoc;
	
	private Point nextPosition;
	private Point currentPosition = new Point(0,0);
	private ArrayList<Bot> top3 = new ArrayList<Bot>();
	private long startTime;
	
	//walking
	int nextPathpos;
	private Point pursuitCoordinates;
	
	//astar
	private ArrayList<Node> closedNodes= new ArrayList<Node>();
	private ArrayList<Node> openNodes= new ArrayList<Node>();
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Point> seenPath = new ArrayList<Point>();
	private ArrayList<Point> sentryTowers = new ArrayList<Point>();
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private boolean checked;
	

	/** Constructor
	 * @param topLeft The top left corner of the area assigned to the surveillance agent
	 * @param bottomRight The bottom right corner of the area assigned to the surveillance agent
	 * @param time The time in milliseconds between every update
	 * @param size The size of the map
	 */
	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		
		//setting up maps
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		pheromoneMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		map = Square.board;
		for(int i = 0; i<sectionMap.length; i++ ) {
			for(int j = 0; j<sectionMap[0].length; j++ ) {
					sectionMap[i][j] = -1;
			}
		}
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		

	 	//creating the agent
		agent = new SurveillanceAgent(new Point((topLeft.x + 1)*1000, (topLeft.y+1)*1000), time, size );
		pursuitAgent = Math.max(Math.min(3, MainApp.amountSA/2), 1);
		explore();
		
	}
	
	/* (non-Javadoc)
	 * @see Bots.Bot#updateMap(java.awt.Point, int)
	 */
	public void updateMap(Point loc, int i) {
		//updating the map with obtained vision
		if(loc.x>=topLeft.x && loc.x<(sectionMap.length + topLeft.x) &&loc.y>=topLeft.y && loc.y<(sectionMap[0].length + topLeft.y)) {
			//adding sentry towers
			if(i == 1 && sectionMap[loc.x - topLeft.x][loc.y - topLeft.y] == -1 ) {
				sentryTowers.add(loc);
			}
			sectionMap[loc.x - topLeft.x][loc.y - topLeft.y] = i;
			pheromoneMap[loc.x - topLeft.x][loc.y- topLeft.y] = 1000;
			if(i == 2) {
				pheromoneMap[loc.x- topLeft.x][loc.y- topLeft.y] = -1;
			}
		}
		
		map[loc.x][loc.y] = i;
		
		//remove seen squares from the seenPath
		for(int j = 0; j<seenPath.size(); j++) {
			if(loc.equals(seenPath.get(j))) {
				seenPath.remove(j);
			}
		}
		
		}

	
	
	/* (non-Javadoc)
	 * @see Bots.Bot#getAgent()
	 */
	public SurveillanceAgent getAgent() {
		return (SurveillanceAgent) agent;
	}
	
	/* (non-Javadoc)
	 * @see Bots.Bot#update()
	 */
	public ArrayList update() {
		
		//check whether agent is on sentry tower or not
		int counter = 0;
		for(int i = 0; i<sentryTowers.size(); i++) {
		if(!agent.getCoordinates().equals(sentryTowers.get(i)) && agent.getCoordinates().distance(sentryTowers.get(i)) > 2) {
			counter++;
		}
			}
		if(counter == sentryTowers.size()) {
			getAgent().setLeftTower(false);
		}
		
		//sounds
		checkLocation(true);
		
		//update the pheromonemap
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1 && pheromoneMap[i][j]!= 0) {
				pheromoneMap[i][j]-=1;}
			
		}}
		
		//check whether exploration is complete
		boolean change = true;
		outerloop:
		for(int k = 0; k<sectionMap.length; k++) {
			for(int l = 0; l<sectionMap[0].length; l++) {
				if(sectionMap[k][l] == -1) {
					change = false;
					break outerloop;
				}
				}
			
			}
		if(change) {
				explorationComplete = true;
		}
		

		//update agent
			//check whether agent is interacting with sentrytower
		if(agent.enterTower || agent.entered || agent.leaveTower) {
				return agent.update();
		}
		else {
		//updating the path if necessary with A* 
		if(!agent.seenIntruders.isEmpty()) {
			//the surveillance agent can see an intruder
			for(int i = 0; i<agent.seenIntruders.size(); i++) {
			startPursuit(agent.seenIntruders.get(i));
		}}
		if(pursuit) {
			//the SA is pursuing an intruder
			pursuitCoordinates = new Point(Math.round(pursuitGoal.x/1000), Math.round(pursuitGoal.y/1000));
			if(path.isEmpty()) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
			else if(!path.get(0).equals(pursuitGoal)) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
		}
		else if ((agent.getCoordinates().x - topLeft.x) < 0 || (agent.getCoordinates().y - topLeft.y)<0) {
			//agent has gone out of its assigned area
			Point backInBounds = new Point(0,0);
			outerloop:
			for(int i = 0; i< sectionMap.length; i++) {
				for(int j = 0; j< sectionMap[0].length; j++) {
					if(sectionMap[i][j] != 2 && sectionMap[i][j] != -1) {
						backInBounds = new Point(i + topLeft.x,j + topLeft.y);
						break outerloop;
					}
			}}
			aStar(agent.getCoordinates(), backInBounds, false, map);
		}
		else if(!explorationComplete && seenPath.isEmpty()) {
			//EXPLORING - the path is empty
			explore();
		}
		else if((!explorationComplete && agent.getCoordinates().equals(path.get(0)))) {
			//EXPLORING - at the last square of the path
			explore();
		}
		else if(!explorationComplete && map[path.get(0).x][path.get(0).y]!=0) {
			//EXPLORING - endgoal is an obstacle
			explore();
		}
		else if(explorationComplete && seenPath.isEmpty()) {
			//System.out.println("surveiling");
			aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y), surveil(), true, sectionMap);
		}
		if(!pursuit && !agent.enterTower && !agent.entered) {
		while(agent.getCoordinates().equals(path.get(0))) {
			if(explorationComplete) {
			aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y), surveil(), true, sectionMap);
			}
			else {
				explore();
			}
			scaleSectionMap();}
		}
		
		//update the actual position of the agent
		if(pursuit && position.equals(pursuitCoordinates)) {
			//SA is very close to the intruder
			return agent.update(agent.findAngle(agent.findVectorPath(pursuitGoal)));
		}
		else if(agent.getCoordinates().equals(path.get(nextPathpos))) {
			if(nextPathpos == 0) {
				return agent.update(agent.findAngle(agent.findVectorPath(path.get(0))));
			}
			else {
			nextPathpos -=1;
			return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));

		}}
		else if( nextPathpos+1 < path.size()) {
			if(!agent.getCoordinates().equals(path.get(nextPathpos + 1)) && !agent.getCoordinates().equals(path.get(nextPathpos))) {
			// agent is off path
				if(distance(path.get(nextPathpos), agent.getCoordinates()) < 3 && agent.getAngle()!= agent.findAngle(agent.findVectorPath(path.get(nextPathpos)))) {
					return agent.update(true,agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));}
				else {
					return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));}
				
		}
			else {
				return agent.update();
			}
			}
		else {
			return agent.update();
		}
		}
		}
	
	/**
	 * @return The position with the lowest pheromone value
	 */
	public Point surveil() {
		min = 10000;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(sectionMap[i][j] != 2) {
				if(pheromoneMap[i][j] <= min ) {
					min = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}}
		}
		//if the bestLoc can be seen from a sentry tower, return sentry tower
		for(int k = 0; k < sentryTowers.size(); k++) {
			if (distance(sentryTowers.get(k), bestLoc)<15 && distance(sentryTowers.get(k), bestLoc)>2 && !agent.getCoordinates().equals(sentryTowers.get(k))) {
				return sentryTowers.get(k);
			}
		}
		return bestLoc;
		
	}
	
	/**
	 * Method which calls a* for the closest unexplored square
	 */
	public void explore() {
		
		//checking whether exploration is complete
		explorationComplete = true;
		int[][] controlExplore = new int[sectionMap.length][sectionMap[0].length];
		outerloop:
		for(int c = 1; c<Math.max(sectionMap.length, sectionMap[0].length); c++) {
			for(int i = -c; i<=c ; i++) {
				for(int j = -c; j<=c; j++) {
					if(Math.abs(i) == c || Math.abs(j)== c) {
						int x = i;
						int y = j;
					if((agent.getCoordinates().x + i - topLeft.x)>= sectionMap.length) {
							x = sectionMap.length - 1 - agent.getCoordinates().x + topLeft.x;
						}
						if(agent.getCoordinates().x + i < topLeft.x) {
							x = topLeft.x - agent.getCoordinates().x;
						}
						if((agent.getCoordinates().y + j - topLeft.y)>= sectionMap[0].length) {
							y = sectionMap[0].length - 1 - agent.getCoordinates().y + topLeft.y;
						}
						if(agent.getCoordinates().y + j < topLeft.y) {
							y = topLeft.y - agent.getCoordinates().y;
						}
					if((agent.getCoordinates().x + x - topLeft.x)< sectionMap.length && agent.getCoordinates().x + x >= topLeft.x && (agent.getCoordinates().y + y - topLeft.y)< sectionMap[0].length && agent.getCoordinates().y + y >= topLeft.y) {
						controlExplore[agent.getCoordinates().x + x - topLeft.x][agent.getCoordinates().y + y - topLeft.y] = 1;
						if(sectionMap[agent.getCoordinates().x + x- topLeft.x][agent.getCoordinates().y + y- topLeft.y] == -1) {
							//Found an unexplored square
							explorationComplete = false;
							Point goal = new Point(agent.getCoordinates().x + x - topLeft.x, agent.getCoordinates().y + y - topLeft.y);
							
							//check whether there is a sentry tower nearby from which you could see the square
							for(int k = 0; k < sentryTowers.size(); k++) {
								if (distance(sentryTowers.get(k), goal)<15 && distance(sentryTowers.get(k), goal)>2 && !agent.getCoordinates().equals(sentryTowers.get(k))) {
									//changing goal to sentry tower
									goal = new Point(sentryTowers.get(k).x - topLeft.x, sentryTowers.get(k).y - topLeft.y);
								}
							}
							aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y),goal , false, sectionMap);
							scaleSectionMap();
							break outerloop;
						}
					}
					
				}
			}
		}
		
		}
		
	
	//print method below to check whether exploration is complete, and which squares are considered for exploration
		
//	System.out.println(agent.getCoordinates() + " -" + topLeft);
//	for(int i = 0; i<sectionMap.length; i++) {
//		for(int j = 0; j<sectionMap[0].length; j++) {
//			if(agent.getCoordinates().equals(new Point(topLeft.x+i, topLeft.y + j))){
//				System.out.print("[A]");
//			}
//			else if(controlExplore[i][j] == 1) {
//				System.out.print("(" + sectionMap[i][j] + ")");
//			}
//			else {
//				System.out.print("[" + sectionMap[i][j] + "]");
//			}
//		}
//		System.out.println();
//	}
		
	}
	
	/**
	 * @param position The last seen position of the intruder
	 */
	public void startPursuit(Point position) {
		
		//find the 3 closest SA's
		top3.clear();
		ArrayList<Bot> agents = MainApp.getSA();
		double[] top3dist = {100000,100000,100000};
		for(int i = 0; i<agents.size(); i++) {
			double distance = distance(agents.get(i).agent.getPosition(), position);
				if(distance < top3dist[2]) {
					if(distance< top3dist[1]) {
						if(distance< top3dist[0]) {
							if(top3.size() >=2) {
							top3.add(2, top3.get(1));
							top3dist[2] = top3dist[1];}
							if(top3.size() >=1) {
							top3.add(1, top3.get(0));
							top3dist[1] = top3dist[0];}
							top3.add(0, agents.get(i));
							top3dist[0] = distance;
						}
						else {
							if(top3.size() >= 2) {
							top3.add(2, top3.get(1));
							top3dist[2] = top3dist[1];
							}
							top3.add(1, agents.get(i));
							top3dist[1] = distance;
						}
					}
					else {
					top3.add(2, agents.get(i));
					top3dist[2] = distance;
				}}
			}
		
		//Taking the appropriate amount of agents to pursuit dependant on the total amount of SA's
		for(int i = 0; i<pursuitAgent; i++) {
			top3.get(i).setPursuit(true);
			top3.get(i).pursuitGoal = position;
		}
	}
	
	/**
	 * Method which scales the sectionMap coordinates to the actual map coordinates
	 */
	public void scaleSectionMap() {
		for(int i = 0; i<path.size(); i++) {
			path.set(i, new Point(path.get(i).x + topLeft.x, path.get(i).y + topLeft.y));
		}
	}
	
	

	/** A star method
	 * @param startPos The starting position of the agent
	 * @param goal The goal position of the agent
	 * @param surveillance Whether it is in the surveillance stage or not
	 * @param board The board it is using, this could be sectionMap or map depending on when used
	 */
	public void aStar(Point startPos, Point goal, boolean surveillance, int[][] board) {

		//reset values
		path.clear();
		seenPath.clear();
		openNodes.clear();
		closedNodes.clear();
		
		startNode = new Node(startPos, distance(startPos, goal));
		openNodes.add(startNode);
		
		while(!openNodes.isEmpty()) {
			
			bestNode = openNodes.get(0);
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
		
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			
			
			if(bestNode.position.x == goal.x && bestNode.position.y == goal.y ) {
				//BASE CASE
				findPath(bestNode);
				nextPathpos = path.size() - 1;
				break;
			}
			else {
				//iterating through the neighbouring squares
				for(int i = -1; i<=1; i++) {
					for(int j = -1; j<=1; j++) {
						//checking whether its out of bounds
						if(bestNode.position.x + i >= 0 && bestNode.position.y + j >= 0 && bestNode.position.x + i < board.length && bestNode.position.y + j < board[0].length) {
						//not the same square
							if(i!=0 || j!=0) {
								if(board[bestNode.position.x + i][bestNode.position.y + j] != 2) {
									if(board[bestNode.position.x + i][bestNode.position.y + j] != 1 || goal.equals(new Point(bestNode.position.x + i, bestNode.position.y + j))) {
								
										checked = false;
										//position of neighbour node
										position = new Point(bestNode.position.x + i, bestNode.position.y + j);
									
										if(surveillance) {
											tempNode = new Node(position, bestNode, distance(position, goal), pheromoneMap[position.x][position.y],  board[position.x][position.y]);
										}
										else {
											tempNode = new Node(position, bestNode, distance(position, goal), board[position.x][position.y]);	
										}
										
										//check whether tempNode is already in closed nodes
										double minimumf = 10000;
										int counter = 0;
										for(int k = 0; k< closedNodes.size(); k++) {
											if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
												counter++;
												if(closedNodes.get(k).f < minimumf) {
													minimumf = closedNodes.get(k).f;
												}}}
										if(tempNode.f< minimumf) {
										openNodes.add(tempNode);
										}
										if(counter>0) {
											checked = true;}
		
										//check whether tempNode is already in open nodes
										if(checked == false) {
											int counter2 = 0;
											for(int k = 0; k< openNodes.size() ; k++) {
												if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
													if(tempNode.f< openNodes.get(k).f) {
														openNodes.remove(k);
														counter2++;
													}
													checked = true;
												}
											}
											if(counter2>0) {
												openNodes.add(tempNode);}
											}
										
										//if neither, add to open nodes
										if(checked == false) {
											if(board[tempNode.position.x][tempNode.position.y] != 2) {
												openNodes.add(tempNode);
											}
										}
									}}

							}
					}}
				}
			
			}
			}
	}
	
	/** Method which finds the path backwards, starting from the last node and working its way back through parent nodes
	 * @param node Position in path
	 */
	public void findPath(Node node){
		path.add(node.position);
		seenPath.add(node.position);
		if(node != startNode) {
		findPath(node.parent); }
	}
	
	
	
	@Override
	public void setSounds(double direction) {
		sounds.add(direction);
		
	}
}
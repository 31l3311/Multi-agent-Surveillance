package Bots;

import java.awt.Point;
import java.util.ArrayList;
import agent.*;
import board.MainApp;

public class surveillanceBot  extends Bot{
	
	//private SurveillanceAgent agent;
	//private int[][] map;
	private int[][] sectionMap;
	private int[][] pheromoneMap;
	private int max;
	private Point bestLoc;
	private boolean explorationComplete;
	private Point topLeft;
	private Point nextPosition;
	private Point currentPosition = new Point(0,0);
	private ArrayList<Bot> top3 = new ArrayList<Bot>();
	
	//walking
	int nextPathpos;
	private Point pursuitCoordinates;
	
	//astar
	private ArrayList<Node> closedNodes= new ArrayList<Node>();
	private ArrayList<Node> openNodes= new ArrayList<Node>();
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Point> badList = new ArrayList<Point>();
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private boolean checked;
	private ArrayList sounds = new ArrayList();
	private double pursuitAgent;

	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		//System.out.println("Instansiating new bot");
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		//map = Square.board;
		for(int i = 0; i<sectionMap.length; i++ ) {
			for(int j = 0; j<sectionMap[0].length; j++ ) {
					sectionMap[i][j] = -1;
			}
		}

		this.topLeft = topLeft;
	 	System.out.println("creating agent");
		agent = new SurveillanceAgent(new Point((topLeft.x + 1)*1000, (topLeft.y+1)*1000), time, size );
		//agent.setPosition(new Point(2,2));
		////System.out.println("agentlalala:" + agent.getCoordinates());
		////System.out.println("agent: " + agent);
	 	//System.out.println("create pheromone map");
		pheromoneMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		pursuitAgent = Math.max(Math.min(3, MainApp.amountSA/2), 1);
	 	//System.out.println("call explore");
		explore();

//		for(int i = 0; i<pheromoneMap.length; i++ ) {
//			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
//				if(map[i][j]!= 0) 
//					{pheromoneMap[i][j] = -1;}
//			}
//		}
		
	}
	
	public void updateMap(Point loc, int i) {
		////System.out.println("Updating map at location: " + loc);
		if(loc.x<sectionMap.length && loc.y<sectionMap[0].length) {
			sectionMap[loc.x][loc.y] = i;
			pheromoneMap[loc.x][loc.y] = 0;
			if(i == 2) {
				pheromoneMap[loc.x][loc.y] = -1;
			}
		}
		map[loc.x][loc.y] = i;	

	}
	
	public SurveillanceAgent getAgent() {
		return (SurveillanceAgent) agent;
	}
	
	public ArrayList update() {
		////System.out.println("method update agent: " + agent);
		//update pheromonemap

		//System.out.println("Location: " + agent.getCoordinates());
		System.out.println("SECTIONMAP 2, 13: " + sectionMap[2][13]);
		System.out.println("topLeft: " + topLeft);

		checkLocation();
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1) {
				pheromoneMap[i][j]+=1;}
			
		}}
		
		//update agent
		if(!agent.seenIntruders.isEmpty()) {
			for(int i = 0; i<agent.seenIntruders.size(); i++) {
			startPursuit(agent.seenIntruders.get(i));
		}}
		if(pursuit) {
			System.out.println("Pursuit goal:" + pursuitGoal);
			pursuitCoordinates = new Point(Math.round(pursuitGoal.x/1000), Math.round(pursuitGoal.y/1000));
			if(path.isEmpty()) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
			else if(!path.get(0).equals(pursuitGoal)) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
		}
		else if(!explorationComplete && path.isEmpty()) {
			explore();
		}
		else if((!explorationComplete && agent.getCoordinates().equals(path.get(0)))) {
			explore();
		}
		else if(!explorationComplete && map[path.get(0).x][path.get(0).y]!=0) {
			//System.out.println("Path to object on: " + path.get(0));
			explore();
			//System.out.println("Path now going to :" + path.get(0));
		}
		while(agent.getCoordinates().equals(path.get(0))) {
			System.out.println("WHILE LOOP");
			aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y), surveil(), true, sectionMap);
			scaleSectionMap();}
		
		System.out.println("Path  " + path);
		System.out.println("Path next position " + path.get(nextPathpos));
//		nextPosition = new Point(path.get(nextPathpos).x + topLeft.x, path.get(nextPathpos).y + topLeft.y);
//		if(nextPathpos +1 < path.size()) {
//		currentPosition = new Point(path.get(nextPathpos + 1).x + topLeft.x, path.get(nextPathpos + 1).y + topLeft.y);}

		
		
		if(pursuit && position.equals(pursuitCoordinates)) {
			//System.out.println("CLOSE TO INTRUDER CLOSE TO INTRUDER ");
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
		//if(nextPathpos+1 < path.size()) {
		else if( nextPathpos+1 < path.size()) {
			if(!agent.getCoordinates().equals(nextPathpos + 1) && !agent.getCoordinates().equals(path.get(nextPathpos))) {

			// ie the agent is not on the current square of the path and not on the next one, so agent is off path
			return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
		}
			else {
				//System.out.println("else statement");
				return agent.update();
			}
			}
		else {
			////System.out.println("else statement");
			return agent.update();
		}
		
		}
	
	public Point surveil() {
		//////System.out.println("method surveil agent: " + agent);
		max = 0;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(pheromoneMap[i][j] >= max ) {
					max = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}
		}
		//////System.out.println("method surveil 2 agent: " + agent);
		return bestLoc;
		
	}
	
	public void explore() {
		//System.out.println("explore agent: " + agent);
		explorationComplete = true;
		
		//while(path.isEmpty()) {
		outerloop:
		for(int i=2; i<sectionMap.length; i++) {
			for(int j=2; j<sectionMap[0].length; j++) {
				if(sectionMap[i][j]<0 && !(agent.getCoordinates().x==i && agent.getCoordinates().y==j) && map[i][j]==0 ) {
					//System.out.println("Next point:" + i +", " + j);
					explorationComplete = false;
					aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y),new Point(i,j), false, sectionMap);
					scaleSectionMap();

					break outerloop;
				}
			}
		}
	}
	
	public void startPursuit(Point position) {
		//System.out.println("Start Pursuit");
		top3.clear();
		ArrayList<Bot> agents = MainApp.getSA();
		double[] top3dist = {100000,100000,100000};
		for(int i = 0; i<agents.size(); i++) {
			double distance = distance(agents.get(i).agent.getPosition(), position);
			//System.out.println("Distance: " + distance);
			//if( distance <=25000) {
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
		//}
		//System.out.println("top 3 size:" + top3.size());
		for(int i = 0; i<pursuitAgent; i++) {
			top3.get(i).setPursuit(true);
			top3.get(i).pursuitGoal = position;
		}
	}
	
	public void scaleSectionMap() {
		for(int i = 0; i<path.size(); i++) {
			path.set(i, new Point(path.get(i).x + topLeft.x, path.get(i).y + topLeft.y));
		}
	}
	

	
	public boolean feasible(int i, int j) {
		for(int k = 0; k<badList.size(); k++) {
			if(badList.get(k).equals(new Point(i,j))) {
				return false;
			}
		}
		return true;
	}
	

	public void aStar(Point startPos, Point goal, boolean surveillance, int[][] board) {
		System.out.println("astar goal: " + goal.x + ", " + goal.y);
		//f = g+h
		//reset values
		path.clear();
		openNodes.clear();
		closedNodes.clear();
		
		
		//startPos = agent.getCoordinates();
		startNode = new Node(startPos, distance(startPos, goal), surveillance);
		openNodes.add(startNode);
		while(!openNodes.isEmpty()) {
		////System.out.println("goal node" + goal.x +", " + goal.y);	
			for(int i = 0; i<openNodes.size(); i++) {
				////System.out.println("Open nodes wl: " + openNodes.get(i));	
				}
			bestNode = openNodes.get(0);
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
			////System.out.println("Best Node position: " + bestNode.position);
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			////System.out.println("best node" + bestNode.position.x +", " + bestNode.position.y);	
			
			if(bestNode.position.x == goal.x && bestNode.position.y == goal.y ) {
				//System.out.println("Goal reached");
				findPath(bestNode);
				nextPathpos = path.size() - 1;
				//System.out.println("Path size  :" + path.size());
				for(int i = 0; i<path.size(); i++) {
					//System.out.println(path.get(i));
				}
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
							//if(map[bestNode.position.x + i][bestNode.position.y + j] == 0 ) {
							checked = false;
							position = new Point(bestNode.position.x + i, bestNode.position.y + j);
							tempNode = new Node(position, bestNode, distance(position, goal), pheromoneMap[position.x][position.y], surveillance, board[position.x][position.y]);
							
							for(int k = 0; k< closedNodes.size(); k++) {
								if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
									if(tempNode.f< closedNodes.get(k).f) {
										openNodes.add(tempNode);
										////System.out.println("add to open nodes 1");
									}
									checked = true;
								}
							}
							
							if(checked == false) {
							for(int k = 0; k< openNodes.size() ; k++) {
								if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
									if(tempNode.f< openNodes.get(k).f) {
										openNodes.add(tempNode);
										////System.out.println("add to open nodes 2");
										openNodes.remove(k);
									}
									checked = true;
								}
							}}
							
							if(checked == false) {
								//if(sectionMap[tempNode.position.x][tempNode.position.y] <= 0){
								if(board[tempNode.position.x][tempNode.position.y] != 2) {
									openNodes.add(tempNode);
									////System.out.println("add to open nodes 3");
								}}
						}}//}
					}}
				}
			
			}
		}
		////System.out.println("a star 2 agent: " + agent);
	}
	
	public void findPath(Node node){
		path.add(node.position);
		if(node != startNode) {
		findPath(node.parent); }
	}
	
	
	
	@Override
	public void setSounds(double direction) {
		sounds.add(direction);
		
	}
}
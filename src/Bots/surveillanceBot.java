package Bots;

import java.awt.Point;
import java.util.ArrayList;

import agent.SurveillanceAgent;
import board.MainApp;

public class surveillanceBot  extends Bot{
	
	//private SurveillanceAgent agent;
	//private int[][] map;
	private int[][] sectionMap;
	private int[][] pheromoneMap;
	private int max;
	private Point bestLoc;
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
	private ArrayList<Point> seenPath = new ArrayList<Point>();
	private ArrayList<Point> sentryTowers = new ArrayList<Point>();
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private boolean checked;
	private ArrayList sounds = new ArrayList();
	private double pursuitAgent;

	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		////System.out.println("Instansiating new bot");
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		//map = Square.board;
		for(int i = 0; i<sectionMap.length; i++ ) {
			for(int j = 0; j<sectionMap[0].length; j++ ) {
					sectionMap[i][j] = -1;
			}
		}

		this.topLeft = topLeft;

	 	//System.out.println("creating agent");
		agent = new SurveillanceAgent(new Point((topLeft.x + 1)*1000, (topLeft.y+1)*1000), time, size );
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
		//System.out.println("Updating map at location: " + loc);
	
		if(loc.x>=topLeft.x && loc.x<(sectionMap.length + topLeft.x) &&loc.y>=topLeft.y && loc.y<(sectionMap[0].length + topLeft.y)) {
			if(i == 1 && sectionMap[loc.x - topLeft.x][loc.y - topLeft.y] == -1 ) {
				//System.out.println("Sentry tower added at : " + loc);
				sentryTowers.add(loc);
			}
			sectionMap[loc.x - topLeft.x][loc.y - topLeft.y] = i;
			pheromoneMap[loc.x - topLeft.x][loc.y- topLeft.y] = 0;
			if(i == 2) {
				pheromoneMap[loc.x- topLeft.x][loc.y- topLeft.y] = -1;
			}
		}
		
		map[loc.x][loc.y] = i;
		for(int j = 0; j<seenPath.size(); j++) {
			if(loc.equals(seenPath.get(j))) {
				seenPath.remove(j);
			}
		}
		
//		for(int k = 0; k<sectionMap.length; k++) {
//			for(int l = 0; l<sectionMap[0].length; l++) {
//				//System.out.print("[" + sectionMap[k][l]+"]");
//				}
//			//System.out.println();
//			}
		
		}

	
	
	public SurveillanceAgent getAgent() {
		return (SurveillanceAgent) agent;
	}
	
	public ArrayList update() {
		System.out.println("UPDATE UPDATE UPDATE UPDATE UPDTA EUPDBAUHDECJKRBCJKBRKJCBJKENBCJKENBCKJBNCJKNE: " + agent);
		//update pheromonemap

		////System.out.println("Location: " + agent.getCoordinates());
		////System.out.println("SECTIONMAP 2, 13: " + sectionMap[2][13]);
		//System.out.println("topLeft: " + topLeft);

		checkLocation(true);
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1) {
				pheromoneMap[i][j]+=1;}
			
		}}
		
		boolean change = true;
		outerloop:
		for(int k = 0; k<sectionMap.length; k++) {
			for(int l = 0; l<sectionMap[0].length; l++) {
				if(sectionMap[k][l] == -1) {
					System.out.println("Found unexplored square");
					System.out.println("and " + explorationComplete);
					change = false;
					break outerloop;
				}
				}
			
			}
		if(change) {
				System.out.println("EXPLORATION COMPLETE");
				explorationComplete = true;
		}
		//update agent
		if(!agent.seenIntruders.isEmpty()) {
			//System.out.println("I SEE AN INTRUDER");
			for(int i = 0; i<agent.seenIntruders.size(); i++) {
			startPursuit(agent.seenIntruders.get(i));
		}}
		if(pursuit) {
			//System.out.println("I AM PURSUING AN INTRUDER");
			//System.out.println("Pursuit goal:" + pursuitGoal);
			pursuitCoordinates = new Point(Math.round(pursuitGoal.x/1000), Math.round(pursuitGoal.y/1000));
			if(path.isEmpty()) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
			else if(!path.get(0).equals(pursuitGoal)) {
				aStar(agent.getCoordinates(), pursuitCoordinates, false, map);
			}
		}
		else if(!explorationComplete && seenPath.isEmpty()) {
			//System.out.println("EXPLORING - path is empty");
			explore();
		}
		else if((!explorationComplete && agent.getCoordinates().equals(path.get(0)))) {
			//System.out.println("EXPLORING - at the end of my path");
			explore();
		}
		else if(!explorationComplete && map[path.get(0).x][path.get(0).y]!=0) {
			////System.out.println("Path to object on: " + path.get(0));
			//System.out.println("EXPLORING - endgoal is object");
			explore();
			////System.out.println("Path now going to :" + path.get(0));
		}
		else if(explorationComplete) {
			aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y), surveil(), true, sectionMap);
		}
		if(!pursuit) {
		while(agent.getCoordinates().equals(path.get(0))) {
			System.out.println("WHILE LOOP");
			if(explorationComplete) {
			aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y), surveil(), true, sectionMap);
			}
			else {
				explore();
			}
			scaleSectionMap();}
		}
		
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
			if(!agent.getCoordinates().equals(path.get(nextPathpos + 1)) && !agent.getCoordinates().equals(path.get(nextPathpos))) {
				//System.out.println("Current coordinates: " + agent.getCoordinates());
				//System.out.println("I AM OFF TRACK");
			// ie the agent is not on the current square of the path and not on the next one, so agent is off path
				//System.out.println("Input to update method for agent: " + agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
				if(distance(path.get(nextPathpos), agent.getCoordinates()) < 3 && agent.getAngle()!= agent.findAngle(agent.findVectorPath(path.get(nextPathpos)))) {
					return agent.update(true,agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));}
				else {
					return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));}
				
		}
			else {
				////System.out.println("else statement");
				return agent.update();
			}
			}
		else {
			//////System.out.println("else statement");
			return agent.update();
		}
		
		}
	
	public Point surveil() {
		System.out.println("method surveil agent: " + agent + " should it surveil? " + explorationComplete);
		max = 0;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(pheromoneMap[i][j] >= max ) {
					max = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}
		}
		for(int k = 0; k < sentryTowers.size(); k++) {
			if (distance(sentryTowers.get(k), bestLoc)<15 && distance(sentryTowers.get(k), bestLoc)>2) {
				return sentryTowers.get(k);
			}
		}
		////////System.out.println("method surveil 2 agent: " + agent);
		return bestLoc;
		
	}
	
	public void explore() {
		System.out.println("explore agent: " + agent);
		explorationComplete = true;
		
		outerloop:
		for(int c = 1; c<Math.max(sectionMap.length, sectionMap[0].length); c++) {
			System.out.println("c: " + c);
			for(int i = -c; i<c ; i++) {
				System.out.println("i: " + i);
				for(int j = -c; j<c; j++) {
					if(Math.abs(i) == c || Math.abs(j)== c) {
					System.out.println("j: " + j);
					//System.out.println("coordinates: " + agent.getCoordinates());
					//System.out.println("sectionMap : " + sectionMap.length + ",  "+ sectionMap[0].length);
					//System.out.println( (agent.getCoordinates().x + i - topLeft.x) + "<" + sectionMap.length + "&&" + (agent.getCoordinates().x + i ) +">" + topLeft.x + "&&" + (agent.getCoordinates().y + j - topLeft.y) + "<" + sectionMap[0].length + "&&" +( agent.getCoordinates().y + j )+ ">" + topLeft.y);
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
						//System.out.println(Math.abs(i) +  "==" + c  + "||" +  Math.abs(j) + "==" + c);
						//if(i!=0 || j !=0) {
						System.out.println("sectionMap value: " + sectionMap[agent.getCoordinates().x + x - topLeft.x][agent.getCoordinates().y + y - topLeft.y]);
						if(sectionMap[agent.getCoordinates().x + x- topLeft.x][agent.getCoordinates().y + y- topLeft.y] == -1) {
							explorationComplete = false;
							System.out.println("exploration not complete and calling astar");
							//System.out.println("SectionMap length: " + sectionMap.length + " sectionMap[0]: " + sectionMap[0].length + " i and j: " + i + ",  " + j);
					
							aStar(new Point(agent.getCoordinates().x - topLeft.x, agent.getCoordinates().y - topLeft.y),new Point(agent.getCoordinates().x + x - topLeft.x, agent.getCoordinates().y + y - topLeft.y), false, sectionMap);
							scaleSectionMap();
							break outerloop;
						}
					}
					
				}
			}
		}
		
		}
		
		System.out.println("Finished outerloop");
	
	for(int i = 0; i<sectionMap.length; i++) {
		for(int j = 0; j<sectionMap[0].length; j++) {
			if(agent.getCoordinates().equals(new Point(topLeft.x+i, topLeft.y + j))){
				System.out.print("[ A ]");
			}
			else {
				System.out.print("[" + sectionMap[i][j] + "]");
			}
		}
		System.out.println();
	}
		
	}
	
	public void startPursuit(Point position) {
		System.out.println("Start Pursuit");
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
		////System.out.println("top 3 size:" + top3.size());
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
	
	

	public void aStar(Point startPos, Point goal, boolean surveillance, int[][] board) {
		//System.out.println("Start pos :" + startPos);
		System.out.println("astar goal: " + goal.x + ", " + goal.y);
		//f = g+h
		//reset values
		path.clear();
		seenPath.clear();
		openNodes.clear();
		closedNodes.clear();
		
		
		//startPos = agent.getCoordinates();
		startNode = new Node(startPos, distance(startPos, goal), surveillance);
		openNodes.add(startNode);
		while(!openNodes.isEmpty()) {
		//////System.out.println("goal node" + goal.x +", " + goal.y);	
			for(int i = 0; i<openNodes.size(); i++) {
				//////System.out.println("Open nodes wl: " + openNodes.get(i));	
				}
			bestNode = openNodes.get(0);
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
			//////System.out.println("Best Node position: " + bestNode.position);
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			//////System.out.println("best node" + bestNode.position.x +", " + bestNode.position.y);	
			
			if(bestNode.position.x == goal.x && bestNode.position.y == goal.y ) {
				////System.out.println("Goal reached");
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
							if(surveillance) {
							tempNode = new Node(position, bestNode, distance(position, goal), pheromoneMap[position.x][position.y],  board[position.x][position.y]);}
							else {
								tempNode = new Node(position, bestNode, distance(position, goal), board[position.x][position.y]);	
							}
							for(int k = 0; k< closedNodes.size(); k++) {
								if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
									if(tempNode.f< closedNodes.get(k).f) {
										openNodes.add(tempNode);
										//////System.out.println("add to open nodes 1");
									}
									checked = true;
								}
							}
							
							if(checked == false) {
							for(int k = 0; k< openNodes.size() ; k++) {
								if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
									if(tempNode.f< openNodes.get(k).f) {
										openNodes.add(tempNode);
										//////System.out.println("add to open nodes 2");
										openNodes.remove(k);
									}
									checked = true;
								}
							}}
							
							if(checked == false) {
								//if(sectionMap[tempNode.position.x][tempNode.position.y] <= 0){
								if(board[tempNode.position.x][tempNode.position.y] != 2) {
									openNodes.add(tempNode);
									//////System.out.println("add to open nodes 3");
								}}
						}}//}
					}}
				}
			
			}
		}
		//////System.out.println("a star 2 agent: " + agent);
	}
	
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
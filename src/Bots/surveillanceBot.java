
import java.awt.Point;
import java.util.ArrayList;

public class surveillanceBot  extends Bot{
	
	//private SurveillanceAgent agent;
	//private int[][] map;
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
	private ArrayList<Point> badList = new ArrayList<Point>();
	private Point startPos;
	private Node startNode;
	private Node bestNode;
	private Node tempNode;
	private boolean checked;
	private ArrayList sounds = new ArrayList();

	public surveillanceBot(Point topLeft, Point bottomRight, int time, Point size){
		//System.out.println("Instansiating new bot");
		sectionMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		for(int i = 0; i<sectionMap.length; i++ ) {
			for(int j = 0; j<sectionMap[0].length; j++ ) {
					sectionMap[i][j] = -1;
			}
		}
		agent = new SurveillanceAgent(new Point(topLeft.x*1000, topLeft.y*1000), time, size );
		//agent.setPosition(new Point(2,2));
		//System.out.println("agentlalala:" + agent.getCoordinates());
		//System.out.println("agent: " + agent);
		pheromoneMap = new int[bottomRight.x - topLeft.x][bottomRight.y - topLeft.y];
		explore();
		map = new int[size.x][size.y];
//		for(int i = 0; i<pheromoneMap.length; i++ ) {
//			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
//				if(sectionMap[i][j]!= 0) 
//					{pheromoneMap[i][j] = -1;}
//			}
//		}
		
	}
	
	public void updateMap(Point loc, int i) {
		//System.out.println("Updating map at location: " + loc);
		if(loc.x<sectionMap.length && loc.y<sectionMap[0].length) {
			sectionMap[loc.x][loc.y] = i;
			pheromoneMap[loc.x][loc.y] = 0;
		}
		map[loc.x][loc.y] = i;
		
	}
	
	public SurveillanceAgent getAgent() {
		return (SurveillanceAgent) agent;
	}
	
	public ArrayList update() {
		//System.out.println("method update agent: " + agent);
		//update pheromonemap
		//System.out.println("Location: " + agent.getCoordinates());
		checkLocation();
		for(int i = 0; i<pheromoneMap.length; i++ ) {
			for(int j = 0; j<pheromoneMap[0].length; j++ ) {
				if(pheromoneMap[i][j]!= -1) {
				pheromoneMap[i][j]+=1;}
			
		}}
		
		//update agent
		if(!explorationComplete && path.isEmpty()) {
			explore();
		}
		else if((!explorationComplete && agent.getCoordinates().equals(path.get(0)))) {
			explore();
		}
		else if(agent.getCoordinates().equals(path.get(0))) {
			aStar(surveil(), true);
		}
		
		//System.out.println("Path next position " + path.get(nextPathpos));
		if(agent.getCoordinates().equals(path.get(nextPathpos))) {
			nextPathpos -=1;
//			System.out.println("Position: " + agent.getCoordinates().x + ", " + agent.getCoordinates().y);
//			System.out.println("Next position" + path.get(nextPathpos).x + ", " + path.get(nextPathpos).y);
//			System.out.println("Vector" + agent.findVectorPath(path.get(nextPathpos)).x + ", " + agent.findVectorPath(path.get(nextPathpos)).y);
//			System.out.println("Angle" + agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
			return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
		}
		else if(!agent.getCoordinates().equals(path.get(nextPathpos+1)) && !agent.getCoordinates().equals(path.get(nextPathpos)) ) {
			// ie the agent is not on the current square of the path and not on the next one, so agent is off path
			return agent.update(agent.findAngle(agent.findVectorPath(path.get(nextPathpos))));
		}
		else {
			//System.out.println("else statement");
			return agent.update();
		}
		
		}
	
	public Point surveil() {
		////System.out.println("method surveil agent: " + agent);
		max = 0;
		for(int i = 0; i<sectionMap.length; i++) {
			for(int j = 0; j<sectionMap[0].length; j++) {
				if(pheromoneMap[i][j] >= max ) {
					max = pheromoneMap[i][j];
					bestLoc = new Point(i,j);
				}
			}
		}
		////System.out.println("method surveil 2 agent: " + agent);
		return bestLoc;
		
	}
	
	public void explore() {
		//System.out.println("explore agent: " + agent);
		explorationComplete = true;
		
		//while(path.isEmpty()) {
		outerloop:
		for(int i=0; i<sectionMap.length; i++) {
			for(int j=0; j<sectionMap[0].length; j++) {
				if(sectionMap[i][j]<0 && !(agent.getCoordinates().x==i && agent.getCoordinates().y==j) ) {
					//System.out.println("Next point:" + i +", " + j);
					explorationComplete = false;
					aStar(new Point(i,j), false);
//					if(path.isEmpty()) {
//						badList.add(new Point(i,j));
//					}
					break outerloop;
				}
			}
		}
		//}
		//System.out.println("explore 2 agent: " + agent);
	}
	
	public boolean feasible(int i, int j) {
		for(int k = 0; k<badList.size(); k++) {
			if(badList.get(k).equals(new Point(i,j))) {
				return false;
			}
		}
		return true;
	}
	
	public void aStar(Point goal, boolean surveillance) {
		System.out.println("astar goal: " + goal.x + ", " + goal.y);
		//f = g+h
		//reset values
		path.clear();
		openNodes.clear();
		closedNodes.clear();
		
		
		startPos = agent.getCoordinates();
		startNode = new Node(startPos, distance(startPos, goal), surveillance);
		openNodes.add(startNode);
		while(!openNodes.isEmpty()) {
		//System.out.println("goal node" + goal.x +", " + goal.y);	
			for(int i = 0; i<openNodes.size(); i++) {
				//System.out.println("Open nodes wl: " + openNodes.get(i));	
				}
			bestNode = openNodes.get(0);
			for(int i = 0; i<openNodes.size(); i++) {
				if(openNodes.get(i).f < bestNode.f) {
					bestNode = openNodes.get(i);
				}
			}
			openNodes.remove(bestNode);
			closedNodes.add(bestNode);
			//System.out.println("best node" + bestNode.position.x +", " + bestNode.position.y);	
			
			if(bestNode.position.x == goal.x && bestNode.position.y == goal.y ) {
				findPath(bestNode);
				nextPathpos = path.size() - 1;
				//System.out.println("Path :");
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
						if(bestNode.position.x + i >= 0 && bestNode.position.y + j >= 0 && bestNode.position.x + i < sectionMap.length && bestNode.position.y + j < sectionMap[0].length) {
						//not the same square
							//if(i!=0 || j!=0) {
								if(sectionMap[bestNode.position.x + i][bestNode.position.y + j] == 0 || sectionMap[bestNode.position.x + i][bestNode.position.y + j] == -1) {
							checked = false;
							position = new Point(bestNode.position.x + i, bestNode.position.y + j);
							tempNode = new Node(position, bestNode, distance(position, goal), pheromoneMap[position.x][position.y], surveillance);
							
							for(int k = 0; k< closedNodes.size(); k++) {
								if(position.x == closedNodes.get(k).position.x && position.y == closedNodes.get(k).position.y ) {
									if(tempNode.f< closedNodes.get(k).f) {
										openNodes.add(tempNode);
										//System.out.println("add to open nodes 1");
									}
									checked = true;
								}
							}
							
							if(checked == false) {
							for(int k = 0; k< openNodes.size() ; k++) {
								if(position.x == openNodes.get(k).position.x && position.y == openNodes.get(k).position.y) {
									if(tempNode.f< openNodes.get(k).f) {
										openNodes.add(tempNode);
										//System.out.println("add to open nodes 2");
										openNodes.remove(k);
									}
									checked = true;
								}
							}}
							
							if(checked == false) {
								if(sectionMap[tempNode.position.x][tempNode.position.y] <= 0){
									openNodes.add(tempNode);
									//System.out.println("add to open nodes 3");
								}}
						}}//}
					}
				}
			
			}
		}
		//System.out.println("a star 2 agent: " + agent);
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
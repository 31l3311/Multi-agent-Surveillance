package Bots;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import agent.*;
import board.*;

public class RandomBot extends Bot{
	private boolean surveillance;
	//private int[][] map;
	private int counter = 0;
	private ArrayList bots;
	private int x;
    private int y;
    private double posx;
    private double posy;

    private int correctorX;
    private int correctorY;

    private double targetVectorPosX;
    private double targetVectorPosY;

    private double runAwayPosX;
    private double runAwayPosY;

    private int counterEscape = 0;
    private Point targetVector = new Point();
    private Point escapeWallVector = new Point();
    private Square square = new Square();

    public RandomBot(boolean surveillance, Point position, int time, Point size){
		this.position = position;
		this.surveillance = surveillance;
		map = new int[size.x][size.y];
		//System.out.println("Random Bot initialised");
		if(surveillance) {
			agent = new SurveillanceAgent(position, time, size);
		}
		else {
			agent = new Intruder(position, time, size);
		}
	//System.out.println("Position: " + agent.getPosition());
	//System.out.println("Coordinates: " + agent.getCoordinates());
	}

	public void setBots(ArrayList bots) {
		this.bots = bots;
	}

	public ArrayList<Bot> getBots(){
		return bots;
	}

	public ArrayList update() {
        //System.out.println("entered" + agent.entered + "enterTower" + agent.enterTower);
        for (int i = 0; i < Square.board.length; i++) {
            for (int j = 0; j < Square.board[i].length; j++) {
                if (Square.board[i][j] == 1) {
                    x = i*20;
                    y = j*20;

                   // if (Math.abs(MainApp.intruders.get(0).getCenterX() - x) <= 15 && Math.abs(MainApp.intruders.get(1).getCenterY() - y) <= 15) {
//						MainApp.circle.setCenterX(sentryx);
//						MainApp.circle.setCenterY(sentryy);
                        if (surveillance && !agent.entered && !agent.enterTower && !agent.leaveTower) {
                            System.out.println("SENTRYTOWER");
                            agent.enterTower();
							agent.position.x = ((x*50) + 500);
							agent.position.y = ((y*50) + 500);
                            return agent.update();
                        }
                    }
                    else if (Square.board[i][j] == 5) {
                        x = i * 20;
                        y = j * 20;
//                        if (Math.abs(MainApp.circle1.getCenterX() - x) <= 15 && Math.abs(MainApp.circle1.getCenterY() - y) <= 15) {
//                            agent.inShade();
//                            //System.out.print("inShade");
//
//                        }
                    } else if (Square.board[i][j] != 5) {
                        x = i * 20;
                        y = j * 20;
//                        if (Math.abs(MainApp.circle1.getCenterX() - x) <= 5 && Math.abs(MainApp.circle1.getCenterY() - y) <= 5 && agent.shade) {
//                            agent.shade = false;
//                            //System.out.println("out of shade");
//                        }
                    }
                }
            }
		checkLocation(true);
        escapeFromWall();
		greedyWalk();
		if(agent.myDirection.size()>2 && !agent.entered && !agent.enterTower && !agent.leaveTower) {
		if(map[agent.myDirection.get(2).x][agent.myDirection.get(2).y] != 0) {
			//System.out.println("I should turn around now!");
			return agent.update(true,(agent.getAngle() + 160));
		}
		if(map[agent.myDirection.get(1).x][agent.myDirection.get(1).y] != 0) {
			//System.out.println("I should turn around now!");
			return agent.update(true,(agent.getAngle() + 160));
			}
		}
		if((counter >= 100 || avoidObjects()) && !agent.entered && !agent.enterTower && !agent.leaveTower) {
			counter = 0;
			return agent.update(changeAngle());
		}
		counter++;
		return agent.update();
	}


	public double changeAngle() {
		double angle = (360*Math.random());
		//System.out.println("New angle = " + angle);
		return angle;
	}

	public void updateMap(Point loc, int i) {
		map[loc.x][loc.y] = i;
		if(i == 1) {
			// implement method to avoid tree or shit
			//System.out.println("I see a tree!");
		}
	}

	/**
	 * This method makes the intruder check in a radius around it.
	 * If it finds a goal it changes its vector to that target
	 * If it finds an agent it attempt to run away. It will have the same vector as the agent,
	 * and since it is faster, it will generally outrun it (in a straight line, that is)
	 */
	public void greedyWalk() {
		for (int i = 0; i < MainApp.botI.size(); i++) {
			for (int j = 0; j < MainApp.botSA.size(); j++) {
				for (int radius = 0; radius < 10; radius++) {
					//Below is checking for agents
//					if(Math.abs(main.botI.get(i).getAgent().getCoordinates().x - main.botSA.get(j).getAgent().getCoordinates().x) < 4 && Math.abs(main.botI.get(i).getAgent().getCoordinates().y - main.botSA.get(j).getAgent().getCoordinates().y) < 4) {
//						Point intruderVector = main.botSA.get(j).getAgent().vector;
//						//agent.update(agent.findAngle(intruderVector));
//						agent.findAngle(intruderVector);
//						System.out.println("I DID THE THING");
//					}

					//Below is the checking for the target
					//System.out.println("1 Coords are " + (main.botI.get(i).getAgent().getCoordinates().x) + " " +(main.botI.get(i).getAgent().getCoordinates().y + radius));
					//System.out.println(" ");
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y + radius).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y + radius).y] == 6) {

						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));
					}
					//loop for a seperate radius in x and y direction for all squares surrounding intruder
					//System.out.println("2 Coords are " + (MainApp.botI.get(i).getAgent().getCoordinates().x + radius) + " " +(MainApp.botI.get(i).getAgent().getCoordinates().y));
					//System.out.println(" ");

					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x + radius, MainApp.botI.get(i).getAgent().getCoordinates().y).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x + radius, MainApp.botI.get(i).getAgent().getCoordinates().y).y] == 6) {
						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));

					}
					//System.out.println("3 Coords are " + (MainApp.botI.get(i).getAgent().getCoordinates().x) + " " +(MainApp.botI.get(i).getAgent().getCoordinates().y - radius));
					//System.out.println(" ");
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y - radius).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y - radius).y] == 6) {

						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));

					}
					//System.out.println("4 Coords are " + (MainApp.botI.get(i).getAgent().getCoordinates().x - radius) + " " +(MainApp.botI.get(i).getAgent().getCoordinates().y));
					//System.out.println(" ");
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x - radius, MainApp.botI.get(i).getAgent().getCoordinates().y).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x - radius, MainApp.botI.get(i).getAgent().getCoordinates().y).y] == 6) {

						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));
					}
				}
			}
		}
	}

	public Point correctBounds(int x, int y) {
		while(x < 0) {
			x++;
		}
		while(x > 49) {
			x--;
		}
		while(y < 0) {
			y++;
		}
		while(y > 49) {
			y--;
		}
		Point correctorPoint = new Point();
		correctorPoint.setLocation(x, y);
		return correctorPoint;
	}

	/**
	 * This method check if the intruder is too close to the agent, and is close to a wall
	 * If it is, then it escapes from the board and the game terminates with message : "Intruders escaped!"
	 */
	public void escapeFromWall() {

			for (int i = 0; i < MainApp.botI.size(); i++) {
				for (int j = 0; j < MainApp.botSA.size(); j++) {
					for (double k = 0; k < 3; k = k + 0.1) {
						if ((Math.abs(MainApp.botI.get(i).position.x - MainApp.botSA.get(j).position.x) < 2000 ||
								Math.abs(MainApp.botI.get(i).position.y - MainApp.botSA.get(j).position.y) < 2000)) {
							if ((MainApp.botI.get(i).position.x - k * 1000 <= 0)) {
								posx = MainApp.botI.get(i).position.x - k * 1000;
							}
							if (MainApp.botI.get(i).position.x + k * 1000 >= 50000) {
								posx = MainApp.botI.get(i).position.x + k * 1000;
							}

							if (MainApp.botI.get(i).position.y - k * 1000 <= 0) {
								posy = MainApp.botI.get(i).position.y - k * 1000;
							}
							if (MainApp.botI.get(i).position.y + k * 1000 >= 50000) {
								posy = MainApp.botI.get(i).position.y + k * 1000;
							}
							if(counterEscape < 60) {
								counterEscape++;
							}
							else if(counterEscape == 60) {
								escapeWallVector.setLocation(posx, posy);
								agent.update(agent.findAngle(escapeWallVector));
								System.out.println("Intruders escaped!");
								counterEscape = 0;
								System.exit(0);
							}

						}
					}
				}
			}
	}

//	public void updateAgent() {
//		agent.update();
//		agent.hear(getBots());
//	}

	public boolean avoidObjects() {
		for( int i = 0; i < agent.myDirection.size(); i++) {
			if (map[agent.myDirection.get(i).x][agent.myDirection.get(i).y] != 0) {
				//System.out.println("I see something at : " + agent.myDirection.get(i).x + ", " + agent.myDirection.get(i).y);
				return true;
			}
		}
		return false;
	}

	@Override
	public Agent getAgent() {
		return agent;
	}

	@Override
	public void setSounds(double direction) {
		// TODO Auto-generated method stub

	}
}

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
    private Point escapeWall = new Point();
    private MainApp main = new MainApp();

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
	//TODO
	public void greedyWalk() {
	}

	/**
	 * This method check if the intruder is too close to the agent, and is close to a wall
	 * If it is, then it escapes from the board and the game terminates with message : "Intruders escaped"
	 */
	public void escapeFromWall() {
		for (int i = 0; i < main.botI.size(); i++) {
			for (int j = 0; j < main.botSA.size(); j++) {
				for (double k = 0; k < 3; k = k + 0.1) {
					if ((Math.abs(main.botI.get(i).position.x - main.botSA.get(j).position.x) < 3000 ||
							Math.abs(main.botI.get(i).position.y - main.botSA.get(j).position.y) < 3000)) {
						if ((main.botI.get(i).position.x - k * 1000 <= 0)) {
							posx = main.botI.get(i).position.x - k * 1000;
						}
						if(main.botI.get(i).position.x + k * 1000 >= 3000) {
							posx = main.botI.get(i).position.x + k * 1000;
						}

						if(main.botI.get(i).position.y - k * 1000 <= 0) {
							posy = main.botI.get(i).position.y - k * 1000;
						}
						if(main.botI.get(i).position.y + k * 1000 >= 3000){
							posy = main.botI.get(i).position.y + k * 1000;
						}
						escapeWall.setLocation(posx, posy);
						//checkLocation(false);
						agent.update(agent.findAngle(escapeWall));
						System.out.println("Intruders escaped!");
						System.exit(0);
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

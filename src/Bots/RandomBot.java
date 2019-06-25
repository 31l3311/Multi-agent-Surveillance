package Bots;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import board.*;
import agent.*;

public class RandomBot extends Bot{
	private boolean surveillance;
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

    private boolean closeToWall;

	/**
	 * Constructor - initializing either of type surveillance or intruder
	 * @param surveillance determines whether we are dealing with an intruder or agent
	 * @param position the initial position of the current bot
	 * @param time time it takes to update game
	 * @param size size of the map
	 */
    public RandomBot(boolean surveillance, Point position, int time, Point size){
		this.position = position;
		this.surveillance = surveillance;
		map = new int[size.x][size.y];
		if(surveillance) {
			agent = new SurveillanceAgent(position, time, size);
		}
		else {
			agent = new Intruder(position, time, size);
		}
	}

	/**
	 * sets bots
	 * @param bots arraylist containing all bots
	 */
	public void setBots(ArrayList bots) {
		this.bots = bots;
	}

	/**
	 * getter for array containing bots
	 * @return array containing bots
	 */
	public ArrayList<Bot> getBots(){
		return bots;
	}

	/**
	 * main update method, allows entering into tower, ensures bot stays
	 * within bounds, and scans for surrounding targets. Gets called recursively to
	 * constantly update bots.
	 * @return update() method on current bot, called recursively
	 */
	public ArrayList update() {
        for (int i = 0; i < Square.board.length; i++) {
            for (int j = 0; j < Square.board[i].length; j++) {
                if (Square.board[i][j] == 1) {
                    x = i*20;
                    y = j*20;
                        if (surveillance && !agent.entered && !agent.enterTower && !agent.leaveTower) {
                            agent.enterTower();
							agent.position.x = ((x*50) + 500);
							agent.position.y = ((y*50) + 500);
                            return agent.update();
                        }
                    }
                    else if (Square.board[i][j] == 5) {
                        x = i * 20;
                        y = j * 20;

                    } else if (Square.board[i][j] != 5) {
                        x = i * 20;
                        y = j * 20;
                    }
                }
            }
		checkLocation(true);
        escapeFromWall();
		greedyWalk();
		if(agent.myDirection.size()>2 && !agent.entered && !agent.enterTower && !agent.leaveTower) {
		if(map[agent.myDirection.get(2).x][agent.myDirection.get(2).y] != 0) {
			return agent.update(true,(agent.getAngle() + 160));
		}
		if(map[agent.myDirection.get(1).x][agent.myDirection.get(1).y] != 0) {
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


	/**
	 * changes the direction vector stochastically
	 * @return new angle
	 */
	public double changeAngle() {
		double angle = (360*Math.random());
		return angle;
	}

	/**
	 * sets current location of map to object i
	 * @param loc point containing coordinates
	 * @param i object corresponding to number
	 */
	public void updateMap(Point loc, int i) {
		map[loc.x][loc.y] = i;
		if(i == 1) {
		}
	}

	/**
	 * This method makes the intruder check in a radius around it.
	 * If it finds a goal it changes its vector to that target.
	 * If it finds an agent it attempt to run away. It will have the same vector as the agent.
	 */
	public void greedyWalk() {
		for (int i = 0; i < MainApp.botI.size(); i++) {
			for (int j = 0; j < MainApp.botSA.size(); j++) {
				for (int radius = 0; radius < 10; radius++) {
					//Below is checking for agents
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y + radius).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y + radius).y] == 6) {

						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));
					}
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x + radius, MainApp.botI.get(i).getAgent().getCoordinates().y).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x + radius, MainApp.botI.get(i).getAgent().getCoordinates().y).y] == 6) {
						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));

					}
					if(square.board[correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y - radius).x][correctBounds(MainApp.botI.get(i).getAgent().getCoordinates().x, MainApp.botI.get(i).getAgent().getCoordinates().y - radius).y] == 6) {

						targetVectorPosX = square.getTargetCoordinates().x;
						targetVectorPosY = square.getTargetCoordinates().y;
						targetVector.setLocation(targetVectorPosX, targetVectorPosY);
						agent.update(agent.findAngle(targetVector));

					}
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

	/**
	 * we ensure that when changing vectors we never go out of bounds. If we do,
	 * We correct to the nearest point that is within bounds.
	 * @param x x coordinate that is being checked of the bot
	 * @param y y coordinate that is being checked of the bot
	 * @return Point containing the corrected bounds
	 */
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
	 * If it is, then it escapes from the board and the game terminates with message : "Intruders escaped!", and
	 * the time taken is written to the file intruderEscapeTimes.txt
	 */
	public void escapeFromWall() {

		for (int i = 0; i < MainApp.botI.size(); i++) {
			for (int j = 0; j < MainApp.botSA.size(); j++) {
				for (double k = 0; k < 3; k = k + 0.1) {
					closeToWall = false;
					if ((Math.abs(MainApp.botI.get(i).getAgent().position.x - MainApp.botSA.get(j).getAgent().position.x) < 1000 &&
							Math.abs(MainApp.botI.get(i).getAgent().position.y - MainApp.botSA.get(j).getAgent().position.y) < 1000)) {
						//System.out.println("1");

						if ((MainApp.botI.get(i).getAgent().position.x - (k * 1000) <= 0)) {
							posx = MainApp.botI.get(i).getAgent().position.x - (k * 1000);
							closeToWall = true;
						}
						if (MainApp.botI.get(i).getAgent().position.x + (k * 1000) >= 50000) {
							posx = MainApp.botI.get(i).getAgent().position.x + (k * 1000);
							closeToWall = true;
						}

						if (MainApp.botI.get(i).getAgent().position.y - (k * 1000) <= 0) {
							posy = MainApp.botI.get(i).getAgent().position.y - (k * 1000);
							closeToWall = true;
						}
						if (MainApp.botI.get(i).getAgent().position.y + (k * 1000) >= 50000) {
							posy = MainApp.botI.get(i).getAgent().position.y + (k * 1000);
							closeToWall = true;
						}

						if (counterEscape < 60) {
							counterEscape++;
						} else if (counterEscape == 60 && closeToWall) {
							//System.out.println("2");
							escapeWallVector.setLocation(posx, posy);
							agent.update(agent.findAngle(escapeWallVector));
							counterEscape = 0;

							//System.out.println("3");
							long endTime = System.currentTimeMillis();
							long timeElapsed = (endTime - MainApp.startTimeProgram) / 1000;
							System.out.println("Execution time in milliseconds: " + timeElapsed);

							try {
								Writer wr = new FileWriter("intruderEscapeTimes.txt", true);
								wr.write(timeElapsed + "" + " ");
								wr.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							System.out.println("Intruders escaped!");
							System.exit(0);
						}
					}
				}
			}
		}
	}

	/**
	 * As long as there is an object in its way (i.e. !=0) we return true.
	 * If no objects are found we return false.
	 * @return boolean true or false depending on there being objects in the way or not
	 */
	public boolean avoidObjects() {
		for( int i = 0; i < agent.myDirection.size(); i++) {
			if (map[agent.myDirection.get(i).x][agent.myDirection.get(i).y] != 0) {
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

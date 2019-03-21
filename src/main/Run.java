
package main;


import java.awt.Point;
import java.util.ArrayList;

import agent.*;

import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import sun.management.resources.agent;
import board.Menu;


public class Run {

	private Timeline GameTimer;
	private SurveillanceAgent agent = new SurveillanceAgent(new Point(0,0));;
	private int time = 50;
	private int[][] board = new int[200][200];
	private ArrayList<SurveillanceAgent> agents;
	private ArrayList<Intruder> intruders;
	private ArrayList<Point> positions;

	public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame mainFrame =
			new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(mainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();

	//GameTimer.stop();
	}
	
	public void setBoard() {
		board[20][20] = 1;
	}

	public void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			//System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			agents.get(j).updateMap(squares.get(i).x , squares.get(i).y, board[squares.get(i).x][squares.get(i).x]);
		}
	}


	public void update() {
		positions.clear();
		for(int i = 0; i<agents.size(); i++) {
		check(agents.get(i).update(time), i);
		//agents.get(i).hear(agents);	
		}
	}
}

package main;

import java.awt.Point;
import java.util.ArrayList;

import agent.SurveillanceAgent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Run {
	
	private Timeline GameTimer;
	private SurveillanceAgent agent = new SurveillanceAgent(new Point(0,0));;
	private int time = 50;
	private int[][] board = new int[200][200];
	
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
	
	public void check(ArrayList<Point> squares) {
		for(int i = 0; i<squares.size(); i++) {
			System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			agent.updateMap(squares.get(i).x , squares.get(i).y, board[squares.get(i).x][squares.get(i).x]);
		}
	}
	
	
	public void update() {
		setBoard();
		check(agent.update(time));
		
	}
}

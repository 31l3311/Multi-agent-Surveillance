package main;

import java.awt.Point;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import agent.*;
import board.*;

import static board.MainApp.gridHeight;
import static board.MainApp.gridWidth;

public class Run {

	private int[][] board;
	private Timeline GameTimer;
	private SurveillanceAgent agent = new SurveillanceAgent(new Point(50,50));;
	private MainApp main = new MainApp();
	private int time = 50;

	private ArrayList<SurveillanceAgent> agents = new ArrayList<>();
	private ArrayList<Intruder> intruders = new ArrayList<>();
	private ArrayList<Point> positions = new ArrayList<>();



	public Run(int[][] board) {
		SurveillanceAgent agent = new SurveillanceAgent(new Point(1000,1000));
		this.board = board;
		agents.add(agent);
	}

	public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame mainFrame =
			new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(mainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();
	}

	public void printBoard() {
		for(int i=0; i<50; i++) {
			for(int j=0; j<50; j++) {
				//Sentry = 1, Wall = 2, horWindow/verWindow = 3, horDoor/verDoor = 4, Tree = 5
				if((board[1][j] == 1) || (board[i][j] == 2) || (board[i][j] == 3) || (board[i][j] == 4) || (board[i][j] == 5)) {
					//do nothing
				}
				else {
					board[i][j] = 0;
				}
			}
		}
	}

	public int[][] getBoard() {
		return board;
	}

	public void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			agents.get(j).updateMap(squares.get(i).x , squares.get(i).y, board[squares.get(i).x][squares.get(i).x]);
		}
		}


	public void update() {
		positions.clear();
		for(int i = 0; i<agents.size(); i++) {
			check(agents.get(i).update(time), i);
			MainApp.circle.setCenterX(agents.get(i).position.x*gridWidth/1000);
			MainApp.circle.setCenterY(agents.get(i).position.y*gridHeight/1000);
			//MainApp.circle.setCenterX(Math.random()*4000*gridWidth);
			//MainApp.circle.setCenterY(Math.random()*4000*gridHeight);
		//agents.get(i).hear(agents);
		}

	}
}

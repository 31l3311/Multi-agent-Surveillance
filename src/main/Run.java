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
	private MainApp main = new MainApp();
	private int time = 50;

	private ArrayList<RandomBot> bots = new ArrayList<>();
	private ArrayList<Intruder> intruders = new ArrayList<>();
	private ArrayList<Point> positions = new ArrayList<>();



	public Run(int[][] board) {
		RandomBot bot = new RandomBot(true, new Point(1000,1000), time, new Point(board.length, board[0].length));
		this.board = board;
		bots.add(bot);
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
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
		}
		}


	public void update() {
		positions.clear();
		for(int i = 0; i<bots.size(); i++) {
			check(bots.get(i).update(), i);
			MainApp.circle.setCenterX(bots.get(i).agent.position.x*gridWidth/1000);
			MainApp.circle.setCenterY(bots.get(i).agent.position.y*gridHeight/1000);
			//MainApp.circle.setCenterX(Math.random()*4000*gridWidth);
			//MainApp.circle.setCenterY(Math.random()*4000*gridHeight);
		//agents.get(i).hear(agents);
		}

	}
}

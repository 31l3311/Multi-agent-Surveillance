package main;



import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import Bots.RandomBot;
import agent.*;
//import apple.laf.JRSUIConstants.Direction;
import board.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


//import static board.MainApp.gridHeight;
//import static board.MainApp.gridWidth;

public class Run {

	private int[][] board;
	private Timeline GameTimer;
	private MainApp main = new MainApp();
	private int time = 50;

	private ArrayList<RandomBot> bots = new ArrayList<>();



	public Run(int[][] board) {
		RandomBot bot = new RandomBot(true, new Point(25000,25000), time, new Point(board.length, board[0].length));
		RandomBot intruder = new RandomBot(false, new Point(1000,1000), time, new Point(board.length, board[0].length));
		this.board = board;
		setOuterWall();
		bots.add(bot);
		bots.add(intruder);
		for(int i = 0; i<bots.size(); i++) {
			bots.get(i).setBots(bots);
		}
		printMap();
	}

	public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame mainFrame =
			new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(mainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();
	}

	public void setOuterWall() {
		for(int i = 0; i< board.length; i++) {
			board[i][0] = 2;
			board[i][board.length - 1] = 2;
			board[0][i] = 2;
			board[board.length - 1][i] = 2;
	}}
	

	public int[][] getBoard() {
		return board;
	}

	public void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			//System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
		}
		}


	public void update() {
		/*for(int i = 0; i<bots.size(); i++) {
			check(bots.get(i).update(), i);
			MainApp.circle.setCenterX(bots.get(i).agent.position.x*main.gridWidth/1000);
			MainApp.circle.setCenterY(bots.get(i).agent.position.y*main.gridHeight/1000);
			MainApp.line.setStartX(bots.get(i).agent.position.x*main.gridWidth/1000);
			MainApp.line.setStartY(bots.get(i).agent.position.y*main.gridHeight/1000);
			MainApp.line.setEndX(bots.get(i).agent.direction().x*main.gridWidth/1000);
			MainApp.line.setEndY(bots.get(i).agent.direction().y*main.gridWidth/1000);
			//MainApp.circle.setCenterX(Math.random()*4000*gridWidth);
			//MainApp.circle.setCenterY(Math.random()*4000*gridHeight);
		//agents.get(i).hear(agents);
		}*/
		check(bots.get(0).update(), 0);
		MainApp.circle.setCenterX(bots.get(0).agent.position.x*main.gridWidth/1000);
		MainApp.circle.setCenterY(bots.get(0).agent.position.y*main.gridHeight/1000);
		MainApp.line.setStartX(bots.get(0).agent.position.x*main.gridWidth/1000);
		MainApp.line.setStartY(bots.get(0).agent.position.y*main.gridHeight/1000);
		MainApp.line.setEndX(bots.get(0).agent.direction().x*main.gridWidth/1000);
		MainApp.line.setEndY(bots.get(0).agent.direction().y*main.gridWidth/1000);
		check(bots.get(1).update(), 1);
		MainApp.circle1.setCenterX(bots.get(1).agent.position.x*main.gridWidth/1000);
		MainApp.circle1.setCenterY(bots.get(1).agent.position.y*main.gridHeight/1000);

		}
	
	public void printMap() {
		for(int j = 0; j<board[0].length; j++) {
			for(int i = 0; i< board.length; i++) {
				System.out.print(board[i][j]);
		}
			System.out.println();
			}
	}
}

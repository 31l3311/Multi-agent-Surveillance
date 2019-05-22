import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
//import apple.laf.JRSUIConstants.Direction;
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
	private double targetX;
	private double targetY;
	private int time = 50;

	private ArrayList<RandomBot> bots = new ArrayList<>();



	public Run(int[][] board) {
		RandomBot bot = new RandomBot(true, new Point(25000,25000), time, new Point(board.length, board[0].length));
		RandomBot intruder = new RandomBot(false, new Point(15000,15000), time, new Point(board.length, board[0].length));
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
		//check if intruder is caught
		if(Math.abs(MainApp.circle.getCenterX() - MainApp.circle1.getCenterX()) <= 20 && Math.abs(MainApp.circle.getCenterY() - MainApp.circle1.getCenterY()) <= 20) {
			System.out.println("Agents won!");
			System.exit(0);
		}

		for(int i = 0; i<board.length; i++) {
			for(int j =0; j<board[i].length; j++) {
				if(board[i][j] == 6) {
					targetX = i*20;
					targetY = j*20;
				}
			}
		}

		System.out.println("targetX is " + targetX);
		System.out.println("targetY is " + targetY);
		//checks if intruders reach target
		if(Math.abs(MainApp.circle1.getCenterX() - targetX) <= 20 && Math.abs(MainApp.circle1.getCenterY() - targetY) <= 20) {
			System.out.println("got here 2");
			System.out.println("Intruders won!");
			System.exit(0);
		}


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

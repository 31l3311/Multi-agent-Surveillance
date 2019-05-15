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
	private int time = 50;
	private ArrayList<RandomBot> bots = new ArrayList<>();
	private ArrayList<IntruderBot> intruders = new ArrayList<>();

	public Run(int[][] board, String type) {
		if(type.equals("Agent")) {
			RandomBot bot = new RandomBot(true, new Point(25000, 25000), time, new Point(board.length, board[0].length));
			this.board = board;
			setOuterWall();
			bots.add(bot);
			for (int i = 0; i < bots.size(); i++) {
				bots.get(i).setBots(bots);
			}
			printMap();
		}

		if(type.equals("Intruder")) {
			IntruderBot intruder = new IntruderBot(true, new Point(5000, 5000), time, new Point(board.length, board[0].length));
			this.board = board;
			setOuterWall();
			intruders.add(intruder);

			for (int i = 0; i < intruders.size(); i++) {
				intruders.get(i).setIntruders(intruders);
			}
			printMap();
		}
	}

	public void startTimer() {
	GameTimer = new Timeline();
	KeyFrame MainFrame = new KeyFrame(Duration.millis(time), e -> update());
	GameTimer.getKeyFrames().add(MainFrame);
	GameTimer.setCycleCount(Timeline.INDEFINITE);
	GameTimer.play();
	}

	public void setOuterWall() {
		for(int i = 0; i< board.length; i++) {
			board[i][0] = 2;
			board[i][board.length - 1] = 2;
			board[0][i] = 2;
			board[board.length - 1][i] = 2;

		}
	}

	public int[][] getBoard() {
		return board;
	}

	public void checkAgent(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			//System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
//			System.out.println("XXXXXXXXXXXXXXXXXXXX: " + squares.get(i));
//			System.out.println("YYYYYYYYYYYYYYYYYYYY: " + board[squares.get(i).x][squares.get(i).x]);

		}
	}

	public void checkIntruder(ArrayList<Point> squares, int j) {
		//System.out.println("SIZE OF SQUARES.SIZE IS " + squares.size());
		for(int i = 0; i<squares.size(); i++) {
			//System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			intruders.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
//			System.out.println("XXXXXXXXXXXXXXXXXXXX: " + squares.get(i));
//			System.out.println("YYYYYYYYYYYYYYYYYYYY: " + board[squares.get(i).x][squares.get(i).x]);
		}
	}

	public void update() {
		updateAgent();
		updateIntruder();
	}

	public void updateAgent() {

		for(int i = 0; i<bots.size(); i++) {
			checkAgent(bots.get(i).updateRandomBot(), i);
			MainApp.circle.setCenterX(bots.get(i).agent.position.x*main.gridWidth/1000);
			MainApp.circle.setCenterY(bots.get(i).agent.position.y*main.gridHeight/1000);
			MainApp.line.setStartX(bots.get(i).agent.position.x*main.gridWidth/1000);
			MainApp.line.setStartY(bots.get(i).agent.position.y*main.gridHeight/1000);
			MainApp.line.setEndX(bots.get(i).agent.direction().x*main.gridWidth/1000);
			MainApp.line.setEndY(bots.get(i).agent.direction().y*main.gridWidth/1000);
		}
	}

	public void updateIntruder() {
		System.out.println("GOT HERE 1");
		if(intruders.size() < 0) {
			System.out.println("Size SMALLER than 0");
		}
		if(intruders.size() > 0) {
			System.out.println("Size LARGER than 0");
		}
		if(intruders.size() == 0) {
			System.out.println("Size EQUAL to 0");
		}
		for(int i = 0; i<intruders.size(); i++) {
			System.out.println("Size of  intruders array is " + intruders.size());
			checkIntruder(intruders.get(i).updateIntruderBot(), i);
			MainApp.circleIntruder.setCenterX(intruders.get(i).intruder.position.x*main.gridWidth/1000);
			MainApp.circleIntruder.setCenterY(intruders.get(i).intruder.position.y*main.gridHeight/1000);
		}
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

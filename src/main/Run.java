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
	private ArrayList<RandomBot> intruders = new ArrayList<>();
	private ArrayList<RandomBot> agents = new ArrayList<>();

	public Run(int[][] board, String type) {
		if(type.equals("Agent")) {
			RandomBot agent = new RandomBot(true, new Point(25000, 25000), time, new Point(board.length, board[0].length));
			this.board = board;
			setOuterWall();
			agents.add(agent);
			bots.add(agent);
//			for (int i = 0; i < agents.size(); i++) {
//				agents.get(i).setBots(agents);
//			}
			printMap();
		}

		if(type.equals("Intruder")) {
			RandomBot intruder = new RandomBot(false, new Point(5000, 5000), time, new Point(board.length, board[0].length));
			this.board = board;
			setOuterWall();
			intruders.add(intruder);
			bots.add(intruder);

//			for (int i = 0; i < intruders.size(); i++) {
//				intruders.get(i).setIntruders(intruders);
//			}
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

	public void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
		}
	}

	public void update() {

		for(int i=0; i<agents.size(); i++) {
			check(agents.get(i).updateRandomBot(), i);
			MainApp.circle.setCenterX(agents.get(i).agent.position.x * main.gridWidth / 1000);
			MainApp.circle.setCenterY(agents.get(i).agent.position.y * main.gridHeight / 1000);
			MainApp.line.setStartX(agents.get(i).agent.position.x * main.gridWidth / 1000);
			MainApp.line.setStartY(agents.get(i).agent.position.y * main.gridHeight / 1000);
			MainApp.line.setEndX(agents.get(i).agent.direction().x * main.gridWidth / 1000);
			MainApp.line.setEndY(agents.get(i).agent.direction().y * main.gridWidth / 1000);
		}
		for(int i=0; i<intruders.size();i++) {
			check(intruders.get(i).updateRandomBot(), i);
			MainApp.circleIntruder.setCenterX(intruders.get(i).agent.position.x * main.gridWidth / 1000);
			MainApp.circleIntruder.setCenterY(intruders.get(i).agent.position.y * main.gridHeight / 1000);
			MainApp.lineIntruder.setStartX(intruders.get(i).agent.position.x * main.gridWidth / 1000);
			MainApp.lineIntruder.setStartY(intruders.get(i).agent.position.y * main.gridHeight / 1000);
			MainApp.lineIntruder.setEndX(intruders.get(i).agent.direction().x * main.gridWidth / 1000);
			MainApp.lineIntruder.setEndY(intruders.get(i).agent.direction().y * main.gridWidth / 1000);
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

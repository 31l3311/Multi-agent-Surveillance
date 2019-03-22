import java.awt.Point;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Run {

	int[][] board;
	private Timeline GameTimer;
	private SurveillanceAgent agent = new SurveillanceAgent(new Point(0,0));;
	private MainApp main = new MainApp();
	private int time = 50;

	public Run(int[][] board) {
		this.board = board;
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

	public void check(ArrayList<Point> squares) {
		for(int i = 0; i<squares.size(); i++) {
			//System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			agent.updateMap(squares.get(i).x , squares.get(i).y, board[squares.get(i).x][squares.get(i).x]);

		}
	}

	public void update() {
		//setBoard();
		check(agent.update(time));
		agent.move(time);

		//Creation surveillance agent graphic

		Rectangle surveillance = new Rectangle();
		surveillance.setWidth(20);
		surveillance.setHeight(20);
		surveillance.setFill(Color.BLUE);
		surveillance.setStroke(Color.BLACK);

		//assigning translation values from fixed in node to anywhere on the board

		int translateX_value = (int) agent.getTranslation()[0];
		int translateY_value = (int) agent.getTranslation()[2];

		//pass to a method in square that uses these values to translate

		System.out.println("GETS HERE!");
	}
}
package board;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import Bots.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainApp extends Application {

	/**
	 * returns width of grid
	 * @return the width of the grid
	 */
    public double getgridWidth() {
        return gridWidth;
    }

	/**
	 * returns height of grid
	 * @return the height of the grid
	 */
	public double getgridHeight() {
        return gridHeight;
    }

	/**
	 * returns amount of cells per row
	 * @return cells per row
	 */
	public int getRowCells() {return rowCells;}

	/**
	 * returns amount of cells per column
	 * @return columns per row
	 */
    public int getColumnCells() {return columnCells;}

	/**
	 * Starts the timer, as well as the start() method.
	 * @param args passed arguments.
	 */
	public static void main(String[] args) {
    	startTimeProgram = System.currentTimeMillis();
		launch(args);
    }

	/**
	 * Starts the program. Draws all squares on the board, and adds all surveillance
	 * agents and bots to the board.
	 * @param primaryStage Stage used for the main game.
	 */
    @Override
    public void start(Stage primaryStage) {

        for( int i=0; i < 50; i++) {
            for( int j=0; j < 50; j++) {
                Square node = new Square( i * gridWidth, j * gridHeight, gridWidth, gridHeight, false, i, j);
                root.getChildren().add(node);
                playfield[i][j] = node;
            }
        }
		board = Square.board;
     	size = new Point(board.length, board[0].length);


		for(int i = 0; i<amountSA; i++) {
			graphicsSA();
			line = new Line();
			directionSA.add(line);
			root.getChildren().add(line);
		}
		for(int i = 0; i<amountI; i++) {
			graphicsIntruder();
			line = new Line();
			directionI.add(line);
			root.getChildren().add(line);
		}
		for(int i = 0; i<amountSA+amountI; i++) {
			sounds();
		}

		splitMap splitm = new splitMap(amountSA, size);
		ArrayList<Point> areas = splitm.startingPoints();
		for(int i = 0; i<areas.size(); i = i+2) {
			bot = new surveillanceBot(areas.get(i), areas.get(i+1), time, size);
			bots.add(bot);
			botSA.add(bot);
		}

		for(int i = 0; i< amountI; i++) {
			bot = new RandomBot(false, new Point(1,10000), time, new Point(board.length, board[0].length));
			bots.add(bot);
			botI.add(bot);
		}

	 	for(int i = 0; i<board.length; i++) {
			for(int j =0; j<board[i].length; j++) {
				if(board[i][j] == 6) {
					target = new Point(i*1000, j*1000);
				}
				}
			}
        BorderPane pane = new BorderPane();
        Menu menu = new Menu(root);
        pane.setLeft(root);
        pane.setRight(menu.createMenu());
        Scene scene = new Scene( pane, windowWidth+menu.menuWidth, windowHeight);
        primaryStage.setScene( scene);
        primaryStage.show();
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Multi Agent Surveillance");
    }

	/**
	 * Initializes graphics for surveillance agent
	 */
	public void graphicsSA() {
        circle = new Circle();
        circle.setCenterX(gridWidth+60 );
        circle.setCenterY(gridHeight+60);
        circle.setRadius(0);
        circle.setFill(Color.BLUE);
     	surveillanceAgents.add(circle);
        root.getChildren().add(circle);
        }

	/**
	 * initializes graphics for intruder
	 */
	public void graphicsIntruder() {
        circle = new Circle();
        circle.setCenterX(gridWidth+60 );
        circle.setCenterY(gridHeight+60 );
        circle.setRadius(0);
        circle.setFill(Color.FUCHSIA);
        intruders.add(circle);
        root.getChildren().add(circle);
        }

	/**
	 * initializes grapihcs for sounds made (not fully working)
	 */
	//TODO
	public void sounds() {
			circle = new Circle();
			circle.setCenterX(gridWidth+60);
			circle.setCenterY(gridHeight+60);
         	circle.setRadius(0);
         	circle.setStroke(Color.BLACK);
            sounds.add(circle);
			root.getChildren().add(circle);
        }

	/**
	 * Updates the locations of both surveillance agents and intruders
	 * @param botSA Arraylist containing all surveillance agents
	 * @param botI Arraylist containing all intruders
	 */
	public static void updateGraphics(ArrayList<Bot> botSA, ArrayList<Bot> botI) {
        for(int i = 0; i<botSA.size(); i++) {

			surveillanceAgents.get(i).setCenterX(botSA.get(i).getAgent().position.x*gridWidth/1000);
			surveillanceAgents.get(i).setCenterY(botSA.get(i).getAgent().position.y*gridWidth/1000);
			directionSA.get(i).setStartX(botSA.get(i).getAgent().position.x*gridWidth/1000);
			directionSA.get(i).setStartY(botSA.get(i).getAgent().position.y*gridHeight/1000);
			directionSA.get(i).setEndX(botSA.get(i).getAgent().direction().x*gridWidth/1000);
			directionSA.get(i).setEndY(botSA.get(i).getAgent().direction().y*gridWidth/1000);
        }
        for(int i = 0; i<botI.size(); i++) {

    		intruders.get(i).setCenterX(botI.get(i).getAgent().position.x*gridWidth/1000);
    		intruders.get(i).setCenterY(botI.get(i).getAgent().position.y*gridWidth/1000);
    		directionI.get(i).setStartX(botI.get(i).getAgent().position.x*gridWidth/1000);
    		directionI.get(i).setStartY(botI.get(i).getAgent().position.y*gridHeight/1000);
    		directionI.get(i).setEndX(botI.get(i).getAgent().direction().x*gridWidth/1000);
    		directionI.get(i).setEndY(botI.get(i).getAgent().direction().y*gridWidth/1000);
        }

        }

	/**
	 * returns array with surveillance agents
	 * @return arraylist of surveillance agents
	 */
	public static ArrayList<Bot> getSA(){
        		return botSA;
        }

	/**
	 * returns array of intruders
	 * @return arraylist of intuder agents
	 */
	public static ArrayList<Bot> getI(){
        		return botI;
        }

	/**
	 * setter for all bot arrays
	 * @param bots sets all bots
	 * @param botSA sets all surveillance agents
	 * @param botI sets all intruders
	 */
	public void setBots(ArrayList bots, ArrayList botSA, ArrayList botI) {
        	this.bots = bots;
        	this.botSA = botSA;
        	this.botI = botI;
        }

	/**
	 * Starts game timer
	 */
	public static void startTimer() {
			KeyFrame mainFrame =
        			new KeyFrame(Duration.millis(time), e -> update());
        	GameTimer.getKeyFrames().add(mainFrame);
        	GameTimer.setCycleCount(Timeline.INDEFINITE);
        	GameTimer.play();
        	}

	/**
	 * Update method. Checks if the agents caught intruders. If that happened, terminate game with message "Agents Won!" and
	 * write time taken to corresponding time agentWinningTimes.txt
	 * Also checks if intruders reached their target. If that happened, terminate game with message "Intruders Won!" and
	 * write time taken to corresponding time intruderWinningTime.txt
	 */
	private static void update() {
    	for(int i = 0; i<botSA.size(); i++) {
    		for(int j = 0; j<botI.size(); j++) {
    			if(botSA.get(i).distance(botSA.get(i).getAgent().getPosition(), botI.get(j).getAgent().getPosition())<= 500) {
    				System.out.println("Agents won!");

					long endTime = System.currentTimeMillis();
					long timeElapsed = (endTime - MainApp.startTimeProgram)/1000;
					System.out.println("Execution time in milliseconds: " + timeElapsed);

					try {
						Writer wr = new FileWriter("agentWinningTimes.txt",true);
						wr.write(timeElapsed + "" + " ");
						wr.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
    				System.exit(0);
    			}
    			if(botI.get(j).distance(botI.get(j).getAgent().getPosition(), target) <= radius) {
    				System.out.println("Intruders won!");
					long endTime = System.currentTimeMillis();
					long timeElapsed = (endTime - MainApp.startTimeProgram)/1000;
					System.out.println("Execution time in milliseconds: " + timeElapsed);

					try {
						Writer wr = new FileWriter("intruderWinningTimes.txt", true);
						wr.write(timeElapsed + "" + " ");
						wr.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}

    				System.exit(0);
    			}
    		}
    	}

    		for(int i = 0; i<bots.size(); i++) {
			check(bots.get(i).update(), i);
		}
    			updateGraphics(botSA, botI);
    			randomSound();
		}

	/**
	 * updates map with current squares
	 * @param squares Ararylist cointaining Points with all coordinates
	 * @param j j'th agent in bots
	 */
    public static void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).y]);
		}
		bots.get(j).setSounds(bots.get(j).getAgent().hear());
	}

	/**
	 * Initializes random sound
	 */
	public void initRandomSound() {
		for(int i = 0; i<board.length/5 ; i++ ) {
			for(int j = 0; i<board.length/5; j++) {
				randomSounds.add(poisson.sample());
				areas.add(new Point(i*5, j*5));
			}
		}}

	/**
	 * Uses poisson distribution to determine when a sound is made randomly
	 * x and y - coordinates of sound are the saved.
	 */
	public static void randomSound() {
		timeLapse = System.currentTimeMillis() - startTime;
		for(int i = 0; i<randomSounds.size(); i++) {
		if((int)randomSounds.get(i) - timeLapse <=  0) {
			int newSample = (int)randomSounds.get(i) + poisson.sample();
			randomSounds.set(i, newSample );
			xCo = (int) ((Math.random()*5000) + areas.get(i).x);
			yCo = (int) ((Math.random()*5000) + areas.get(i).y);
			for(int k = 0; k<bots.size(); k++) {
				if(bots.get(k).distance(new Point(xCo, yCo), bots.get(k).position) < 5000) {
					//alert bot k
					Point vector = new Point(bots.get(k).position.x - xCo, bots.get(k).position.y - yCo);
					double angle = bots.get(k).getAgent().findAngle(vector);
					NormalDistribution normal = new NormalDistribution(angle, 10);
					double direction = normal.sample();
					if(direction == 0) {direction = 360;}
					bots.get(k).setSounds(direction);
					
				}
			}
		}	
		}}

	static double windowWidth = 1000;
    static double windowHeight = 1000;
    static int rowCells = 50;
    static int columnCells = 50;
    public static double gridWidth = windowWidth / rowCells;
    public static double gridHeight = windowHeight / columnCells;
    private Square[][] playfield = new Square[rowCells][columnCells];
    public static ArrayList<Circle> surveillanceAgents = new ArrayList<Circle>();
    public static ArrayList<Circle> intruders = new ArrayList<Circle>();
    public static ArrayList<Line> directionSA = new ArrayList<Line>();
    public static ArrayList<Line> directionI = new ArrayList<Line>();
    public static ArrayList<Circle> sounds = new ArrayList<Circle>();
    public static Circle circle;
    public static Line line;
    public static Group root = new Group();
	public static int amountSA = 3;
	public static int amountI = 2;
	public static ArrayList<Bot> bots = new ArrayList<Bot>();
	public static ArrayList<Bot> botSA = new ArrayList<Bot>();
	public static ArrayList<Bot> botI = new ArrayList<Bot>();
 	private static Timeline GameTimer = new Timeline();
	private static final int time = 50;
	private static double startTime;
	private static double timeLapse;
	public static long startTimeProgram;
	private static PoissonDistribution poisson = new PoissonDistribution(600000);
	private static ArrayList randomSounds = new ArrayList();
	private static ArrayList<Point> areas = new ArrayList<Point>();
	private double distance;
	private static int xCo;
	private static int yCo;
	public static int[][] board;
	private Point size;
	private static Bot bot;
	private static Point target;
	private static int radius = 1000;

}




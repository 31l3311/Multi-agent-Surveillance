package board;

import java.awt.Point;
import java.util.ArrayList;


import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;

import Bots.Bot;
import Bots.RandomBot;
import Bots.surveillanceBot;
import agent.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

//import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;

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

    public double getgridWidth() {
        return gridWidth;
    }
    public double getgridHeight() {
        return gridHeight;
    }
    public int getRowCells() {return rowCells;}
    public int getColumnCells() {return columnCells;}

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
    	System.out.println("create squares");
        for( int i=0; i < 50; i++) {
            for( int j=0; j < 50; j++) {

                // create node
                Square node = new Square( i * gridWidth, j * gridHeight, gridWidth, gridHeight, false, i, j);
                // add node to group
                root.getChildren().add(node);
                // add to playfield for further reference using an array
                playfield[i][j] = node;
            }
        }

     	System.out.println("create board");
        board = Square.board;
     	System.out.println("create circles");

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
	 	System.out.println("create bots");
		bot = new surveillanceBot(new Point(1,1), new Point(25,25), time, new Point(50,50));
		bots.add(bot);
		botSA.add(bot);
		bot = new surveillanceBot(new Point(25,1), new Point(50,25), time, new Point(50,50));
		bots.add(bot);
		botSA.add(bot);
		bot = new surveillanceBot(new Point(1,25), new Point(25,50), time, new Point(50,50));
		bots.add(bot);
		botSA.add(bot);
		bot = new surveillanceBot(new Point(25,25), new Point(50,50), time, new Point(50,50));
		bots.add(bot);
		botSA.add(bot);
		for(int i = 0; i< amountI; i++) {
			bot = new RandomBot(false, new Point(10000,10000), time, new Point(board.length, board[0].length));
			bots.add(bot);
			botI.add(bot);
		}
	 	System.out.println("finished bots");

        //run = new Run();
        //run.startTimer();
	 	System.out.println("create pane");
        BorderPane pane = new BorderPane();
        Menu menu = new Menu(root);
        pane.setLeft(root);
        pane.setRight(menu.createMenu());
     	System.out.println("create scene");
        Scene scene = new Scene( pane, windowWidth+menu.menuWidth, windowHeight);
        primaryStage.setScene( scene);
        primaryStage.show();
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Multi Agent Surveillance");


    }

    public void graphicsSA() {
        circle = new Circle();
        circle.setCenterX(gridWidth+60 );
        circle.setCenterY(gridHeight+60);
        circle.setRadius(0);
        circle.setFill(Color.BLUE);
     	surveillanceAgents.add(circle);
        root.getChildren().add(circle);
        }

        public void graphicsIntruder() {
        circle = new Circle();
        circle.setCenterX(gridWidth+60 );
        circle.setCenterY(gridHeight+60 );
        circle.setRadius(0);
        circle.setFill(Color.FUCHSIA);
        intruders.add(circle);
        root.getChildren().add(circle);
        }

        public void sounds() {
        	 	circle = new Circle();
        	 	circle.setCenterX(gridWidth+60);
        	 	circle.setCenterY(gridHeight+60);
         	circle.setRadius(0);
         	circle.setStroke(Color.BLACK);
            sounds.add(circle);
             root.getChildren().add(circle);
        }
        
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

        
        public static ArrayList<Bot> getSA(){
        		return botSA;
        }
        
        public static ArrayList<Bot> getI(){
        		return botI;
        }
        
        public void setBots(ArrayList bots, ArrayList botSA, ArrayList botI) {
        	this.bots = bots;
        	this.botSA = botSA;
        	this.botI = botI;
        }
        
        public static void startTimer() {
			KeyFrame mainFrame =
        			new KeyFrame(Duration.millis(time), e -> update());
        	GameTimer.getKeyFrames().add(mainFrame);
        	GameTimer.setCycleCount(Timeline.INDEFINITE);
        	GameTimer.play();
        	}
        
    private static void update() {
    		for(int i = 0; i<bots.size(); i++) {
			check(bots.get(i).update(), i);
		}
    			updateGraphics(botSA, botI);
    			randomSound();
		}
    
    public static void check(ArrayList<Point> squares, int j) {
		for(int i = 0; i<squares.size(); i++) {
			////System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).y]);
		}
		bots.get(j).setSounds(bots.get(j).getAgent().hear());
	}
	
	public void initRandomSound() {
		for(int i = 0; i<board.length/5 ; i++ ) {
			for(int j = 0; i<board.length/5; j++) {
				randomSounds.add(poisson.sample());
				areas.add(new Point(i*5, j*5));
			}
		}}
	
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
    public static ArrayList<Circle>   intruders = new ArrayList<Circle>();
    public static ArrayList<Line> directionSA = new ArrayList<Line>();
    public static ArrayList<Line> directionI = new ArrayList<Line>();
    public static ArrayList<Circle> sounds = new ArrayList<Circle>();
    public static Circle circle;
    public static Line line;
    public static Group root = new Group();
	public static int amountSA = 5;
	public static int amountI = 3;

	//public static ArrayList<Bot> bots, botSA, botI;
	public static ArrayList<Bot> bots = new ArrayList<Bot>();
	public static ArrayList<Bot> botSA = new ArrayList<Bot>();
	public static ArrayList<Bot> botI = new ArrayList<Bot>();
 	private static Timeline GameTimer = new Timeline();
	private static final int time = 50;
	
	private static double startTime;
	private static double timeLapse;
	private static PoissonDistribution poisson = new PoissonDistribution(600000);
	private static ArrayList randomSounds = new ArrayList();
	private static ArrayList<Point> areas = new ArrayList<Point>();
	private double distance;
	private static int xCo;
	private static int yCo;
	public static int[][] board;
	private static Bot bot;
    
}




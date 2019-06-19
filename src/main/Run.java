//package main;
//
//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//
//import org.apache.commons.math3.distribution.NormalDistribution;
//import org.apache.commons.math3.distribution.PoissonDistribution;
//
//import Bots.Bot;
//import Bots.RandomBot;
//import Bots.surveillanceBot;
//import agent.*;
//import board.MainApp;
//
////import apple.laf.JRSUIConstants.Direction;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Line;
//import javafx.scene.shape.Rectangle;
//import javafx.util.Duration;
//
//
////import static board.MainApp.gridHeight;
////import static board.MainApp.gridWidth;
//
//public class Run {
//
//	private int[][] board;
//	private Timeline GameTimer;
//	private MainApp main = new MainApp(); 
//	private double targetX;
//	private double targetY;
//	private int time = 50;
//	private Bot bot;
//	private double distance;
//	private int xCo, yCo;
//	private double startTime, timeLapse;
//	private PoissonDistribution poisson = new PoissonDistribution(600000);
//	private ArrayList randomSounds = new ArrayList();
//	private ArrayList<Point> areas = new ArrayList<Point>();
//
//	private ArrayList<Bot> bots = new ArrayList<>();
//	private ArrayList<Bot> botSA = new ArrayList<>();
//	private ArrayList<Bot> botI = new ArrayList<>();
//
//
//
//	public Run(int[][] board) {
//		this.board = board;
//		setOuterWall();
//		bots.clear();
//		botSA.clear();
//		botI.clear();
////		for(int i = 0; i<main.amountSA; i++) {
////			bot = new surveillanceBot(new Point(1,1), new Point(25,25), time, new Point(50,50));
////			bots.add(bot);
////			botSA.add(bot);
////		}
//		bot = new surveillanceBot(new Point(1,1), new Point(25,25), time, new Point(50,50));
//		bots.add(bot);
//		botSA.add(bot);
//		bot = new surveillanceBot(new Point(25,1), new Point(50,25), time, new Point(50,50));
//		bots.add(bot);
//		botSA.add(bot);
//		bot = new surveillanceBot(new Point(1,25), new Point(25,50), time, new Point(50,50));
//		bots.add(bot);
//		botSA.add(bot);
//		bot = new surveillanceBot(new Point(25,25), new Point(50,50), time, new Point(50,50));
//		bots.add(bot);
//		botSA.add(bot);
//		for(int i = 0; i<main.amountI; i++) {
//			bot = new RandomBot(false, new Point(10000,10000), time, new Point(board.length, board[0].length));
//			bots.add(bot);
//			botI.add(bot);
//		}
////		bot = new surveillanceBot(new Point(1,1), new Point(25,25), time, new Point(50,50));
////		bot2 = new surveillanceBot(new Point(1,25), new Point(25,50), time, new Point(50,50));
////		bot3= new surveillanceBot(new Point(25,1), new Point(50,25), time, new Point(50,50));
////		//System.out.println("surveillance bot: " + bot);
////		//System.out.println("surveillance agent: " + bot.getAgent());
////		RandomBot intruder = new RandomBot(false, new Point(10000,10000), time, new Point(board.length, board[0].length));
////		
////		bots.add(bot);
////		bots.add(bot2);
////		bots.add(bot3);
////		botSA.add(bot);
////		botSA.add(bot2);
////		botSA.add(bot3);
////		bots.add(intruder);
////		botI.add(intruder);
//		main.setBots(bots, botSA, botI);
//		for(int i = 0; i<botSA.size(); i++) {
//			main.graphicsSA();
//		}
//		for(int i = 0; i<botI.size(); i++) {
//			main.graphicsIntruder();
//		}
//		for(int i = 0; i<bots.size(); i++) {
//			main.sounds();
//		}
//		printMap();
//		for(int i = 0; i<board.length; i++) {
//			for(int j =0; j<board[i].length; j++) {
//				if(board[i][j] == 6) {
//					targetX = i*20;
//					targetY = j*20;
//				}
//			}
//		}
//	}
//
//	public void startTimer() {
//	GameTimer = new Timeline();
//	KeyFrame mainFrame =
//			new KeyFrame(Duration.millis(time), e -> update());
//	GameTimer.getKeyFrames().add(mainFrame);
//	GameTimer.setCycleCount(Timeline.INDEFINITE);
//	GameTimer.play();
//	}
//
//	public void setOuterWall() {
//		for(int i = 0; i< board.length; i++) {
//			board[i][0] = 2;
//			board[i][board.length - 1] = 2;
//			board[0][i] = 2;
//			board[board.length - 1][i] = 2;
//	}}
//	
//
//	public int[][] getBoard() {
//		return board;
//	}
//
//	public void check(ArrayList<Point> squares, int j) {
//		for(int i = 0; i<squares.size(); i++) {
//			////System.out.println(i + ",  " + squares.get(i).x + ", " + squares.get(i).y);
//			bots.get(j).updateMap(squares.get(i), board[squares.get(i).x][squares.get(i).x]);
//		}
//		bots.get(j).setSounds(hear(bots.get(j).getAgent()));
//	}
//	
//	public void initRandomSound() {
//		for(int i = 0; i<board.length/5 ; i++ ) {
//			for(int j = 0; i<board.length/5; j++) {
//				randomSounds.add(poisson.sample());
//				areas.add(new Point(i*5, j*5));
//			}
//		}}
//	
//	public void randomSound() {
//		timeLapse = System.currentTimeMillis() - startTime;
//		for(int i = 0; i<randomSounds.size(); i++) {
//		if((int)randomSounds.get(i) - timeLapse <=  0) {
//			int newSample = (int)randomSounds.get(i) + poisson.sample();
//			randomSounds.set(i, newSample );
//			xCo = (int) ((Math.random()*5000) + areas.get(i).x);
//			yCo = (int) ((Math.random()*5000) + areas.get(i).y);
//			for(int k = 0; k<bots.size(); k++) {
//				if(bots.get(k).distance(new Point(xCo, yCo), bots.get(k).position) < 5000) {
//					//alert bot k
//					Point vector = new Point(bots.get(k).position.x - xCo, bots.get(k).position.y - yCo);
//					double angle = bots.get(k).getAgent().findAngle(vector);
//					NormalDistribution normal = new NormalDistribution(angle, 10);
//					double direction = normal.sample();
//					if(direction == 0) {direction = 360;}
//					bots.get(k).setSounds(direction);
//					
//				}
//			}
//		}	
//		}}
//
//	public double hear(Agent agent) {
//		//System.out.println("Hear method called");
//		
//		for(int i = 0; i<bots.size(); i++) {
//			if(!bots.get(i).getAgent().equals(agent)) {
//			Agent curAgent = bots.get(i).getAgent();
//			//System.out.println("Checking agent: " + curAgent);
//		distance = Math.sqrt(Math.pow((agent.getPosition().x - curAgent.getPosition().x), 2) + Math.pow((agent.getPosition().y - curAgent.getPosition().y), 2));
//		//System.out.println("distance = " + distance);
//		//System.out.println("curAgent.speed = " + curAgent.speed);
//		double speed = curAgent.speed * 1000;
//		if((speed < 0.5 && distance<1000) ||
//		   (speed >= 0.5 && speed<1 && distance<3000)	||
//		   (speed >= 1 && speed<2 && distance<5000) ||
//		   (speed >= 2 && distance<10000) ||
//		   	curAgent.loudDoor && distance<5000 ||
//		   	curAgent.openWindow && distance < 10000
//				) {
//			//System.out.println("I hear a sound!! Yay");
//			Point vector = new Point(agent.getPosition().x - curAgent.getPosition().x, agent.getPosition().y - curAgent.getPosition().y);
//			double angle = agent.findAngle(vector);
//			NormalDistribution normal = new NormalDistribution(angle, 10);
//			double direction = normal.sample();
//			if(direction == 0) {direction = 360;}
//			return direction;
//		}}}
//		return 0;
//		}
//
//	public void update() {
//		//check if intruder is caught
////		if(Math.abs(MainApp.circle.getCenterX() - MainApp.circle1.getCenterX()) <= 20 && Math.abs(MainApp.circle.getCenterY() - MainApp.circle1.getCenterY()) <= 20) {
////			System.out.println("Agents won!");
////			System.exit(0);
////		}
//
//		//System.out.println("targetX is " + targetX);
//		//System.out.println("targetY is " + targetY);
//		//checks if intruders reach target
////		if(Math.abs(MainApp.circle1.getCenterX() - targetX) <= 20 && Math.abs(MainApp.circle1.getCenterY() - targetY) <= 20) {
////			//System.out.println("got here 2");
////			System.out.println("Intruders won!");
////			System.exit(0);
////		}
//		for(int i = 0; i<bots.size(); i++) {
//			check(bots.get(i).update(), i);
//		}
//		main.updateGraphics(botSA, botI);
//		for(int i = 0; i<bots.size(); i++) {
//			//System.out.println("Bot  " + i + " location :" + bots.get(i).position);
//		}
//		randomSound();
//		//System.out.println("Circle: " + MainApp.circle.getCenterX());
//		//System.out.println("bots: " + bots.get(0));
//		//System.out.println("surveillance bot: " + bot);
//		//System.out.println("getAgent(): " + bots.get(0).getAgent());
////		MainApp.circle.setCenterX(bots.get(0).getAgent().position.x*main.gridWidth/1000);
////		MainApp.circle.setCenterY(bots.get(0).getAgent().position.y*main.gridHeight/1000);
////		MainApp.line.setStartX(bots.get(0).getAgent().position.x*main.gridWidth/1000);
////		MainApp.line.setStartY(bots.get(0).getAgent().position.y*main.gridHeight/1000);
////		MainApp.line.setEndX(bots.get(0).getAgent().direction().x*main.gridWidth/1000);
////		MainApp.line.setEndY(bots.get(0).getAgent().direction().y*main.gridWidth/1000);
////		
////		MainApp.circle1.setCenterX(bots.get(1).getAgent().position.x*main.gridWidth/1000);
////		MainApp.circle1.setCenterY(bots.get(1).getAgent().position.y*main.gridHeight/1000);
//		
//
//		}
//	
//	public void printMap() {
//		for(int j = 0; j<board[0].length; j++) {
//			for(int i = 0; i< board.length; i++) {
//				//System.out.print(board[i][j]);
//		}
//			//System.out.println();
//			}
//	}
//}

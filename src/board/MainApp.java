package board;

import java.awt.Point;
import java.util.ArrayList;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;

import Bots.Bot;
import Bots.RandomBot;
import Bots.surveillanceBot;
import agent.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


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

        //run = new Run();
        //run.startTimer();
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
        
        public void updateGraphics(ArrayList<Bot> botSA, ArrayList<Bot> botI) {
    		//System.out.println("Circle: " + MainApp.circle.getCenterX());
    		//System.out.println("bots: " + bots.get(0));
    		//System.out.println("surveillance bot: " + bot);
    		//System.out.println("getAgent(): " + bots.get(0).getAgent());
        for(int i = 0; i<botSA.size(); i++) {
        		surveillanceAgents.get(i).setCenterX(botSA.get(i).getAgent().position.x*gridWidth/1000);
        		surveillanceAgents.get(i).setCenterY(botSA.get(i).getAgent().position.y*gridWidth/1000);
        		directionSA.get(i).setStartX(botSA.get(i).getAgent().position.x*gridWidth/1000);
        		directionSA.get(i).setStartY(botSA.get(i).getAgent().position.y*gridHeight/1000);
        		directionSA.get(i).setEndX(botSA.get(i).getAgent().direction().x*gridWidth/1000);
        		directionSA.get(i).setEndY(botSA.get(i).getAgent().direction().y*gridWidth/1000);
        }
        for(int i = 0; i<botI.size(); i++) {
//        	System.out.println("Intruders:" + intruders);
//        	System.out.println("Intruders.get(i):" + intruders.get(i));
//        	System.out.println("botI" + botI);
//        	System.out.println("botI.get(i) : " + botI.get(i));
//        	System.out.println("botI.get(i).position : " + botI.get(i).position);
    		intruders.get(i).setCenterX(botI.get(i).getAgent().position.x*gridWidth/1000);
    		intruders.get(i).setCenterY(botI.get(i).getAgent().position.y*gridWidth/1000);
    		directionI.get(i).setStartX(botI.get(i).getAgent().position.x*gridWidth/1000);
    		directionI.get(i).setStartY(botI.get(i).getAgent().position.y*gridHeight/1000);
    		directionI.get(i).setEndX(botI.get(i).getAgent().direction().x*gridWidth/1000);
    		directionI.get(i).setEndY(botI.get(i).getAgent().direction().y*gridWidth/1000);
        }
    		
        }
        
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
    public static Circle circle1;
    public static Line line;
    public static Group root = new Group();
	public static int amountSA = 5;
	public static int amountI = 3;
    
}
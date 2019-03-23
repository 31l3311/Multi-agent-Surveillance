package board;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

        Group root = new Group();

        for( int i=0; i < 50; i++) {
            for( int j=0; j < 50; j++) {

                // create node
                Square node = new Square( i * gridWidth, j * gridHeight, gridWidth, gridHeight, false);
                // add node to group
                root.getChildren().add(node);
                // add to playfield for further reference using an array
                playfield[i][j] = node;
            }
        }

        circle = new Circle();
        circle.setCenterX(gridWidth);
        circle.setCenterY(gridHeight);
        circle.setRadius(0);
        circle.setFill(Color.BLUE);
        root.getChildren().add(circle);

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
    static double windowWidth = 1000;
    static double windowHeight = 1000;

    static int rowCells = 50;
    static int columnCells = 50;

    public static double gridWidth = windowWidth / rowCells;
    public static double gridHeight = windowHeight / columnCells;
    private Square[][] playfield = new Square[rowCells][columnCells];
    public static Circle circle;
}
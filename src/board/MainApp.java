import javafx.application.Application;

import javax.swing.JComboBox;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainApp extends Application {

    public double getgridWidth() {
        return gridWidth;
    }
    public double getgridHeight() {
        return gridHeight;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        // initialize playfield
        for( int i=0; i < rowCells; i++) {
            for (int j = 0; j < columnCells; j++) {
//                if (i == 0|| i==50) {
                Square test = new Square(i * gridWidth, j * gridHeight, gridWidth, gridHeight, false);
                root.getChildren().add(test);
                Rectangle borderWalls = new Rectangle(i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                borderWalls.setHeight(gridHeight);
                borderWalls.setWidth(gridWidth);
                borderWalls.setStroke(Color.DARKSLATEGREY);
                borderWalls.setFill(Color.DARKSLATEGREY);
                root.getChildren().addAll(borderWalls);
                playfield[i][j] = test;
            }
        }

        for( int i=1; i < 49; i++) {
            for( int j=1; j < 49; j++) {

                // create node
                Square node = new Square( i * gridWidth, j * gridHeight, gridWidth, gridHeight, false);
                // add node to group
                root.getChildren().add(node);
                // add to playfield for further reference using an array
                playfield[i][j] = node;
            }
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
    static double windowWidth = 1000;
    static double windowHeight = 1000;

    static int rowCells = 50;
    static int columnCells = 50;

    public double gridWidth = windowWidth / rowCells;
    public double gridHeight = windowHeight / columnCells;
    private Square[][] playfield = new Square[rowCells][columnCells];
}
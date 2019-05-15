

import javafx.scene.input.MouseEvent;
import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import java.util.Properties;

public class Square extends StackPane {

    MainApp w = new MainApp();
    Properties p = new Properties();
    public static boolean getCleared = false;
    public static int[][] board = new int[50][50];


    public Square(double x, double y, double width, double height, boolean getCleared) {

        // create rectangle
        Rectangle rectangle = new Rectangle(x, y, width, height);

        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.LIGHTGREEN);

        setOnMousePressed(e->mousePressedOnSquare(e));

        // set position
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(rectangle);
        if(getCleared) {
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTGREEN);
            setOnMousePressed(e -> mousePressedOnSquare(e));
            // set position
            setTranslateX(x);
            setTranslateY(y);
            getChildren().removeAll(rectangle);
            System.out.println("Break rectangles");
        }
        else if(getCleared = false){
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTGREEN);
            setOnMousePressed(e -> mousePressedOnSquare(e));
            // set position
            setTranslateX(x);
            setTranslateY(y);
            getChildren().addAll(rectangle);
            System.out.println("Build rectangles");
        }
    }


    void mousePressedOnSquare(MouseEvent e) {
        if(Menu.state == "Sentry") {
            Circle sentry = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
            sentry.setStroke(Color.BLACK);
            sentry.setFill(Color.RED);
            //setTranslateX(e.getSceneX() - 300 );
            //setTranslateY(e.getSceneY() - 300 );
            getChildren().add(sentry);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 1;
        }
        else if(Menu.state == "Wall") {
            Rectangle Wall = new Rectangle();
            Wall.setHeight(w.gridHeight);
            Wall.setWidth(w.gridWidth);
            Wall.setFill(Color.GRAY);
            Wall.setStroke(Color.BLACK);
            getChildren().add(Wall);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 2;

        }
        else if(Menu.state == "horWindow") {

            Rectangle horWindow = new Rectangle();
            horWindow.setHeight(w.gridHeight/4);
            horWindow.setWidth(w.gridWidth);
            horWindow.setStroke(Color.BLACK);
            horWindow.setFill(Color.WHITE);
            getChildren().add(horWindow);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 3;

        }
        else if(Menu.state == "verWindow") {
            Rectangle verWindow = new Rectangle();
            verWindow.setHeight(w.gridHeight);
            verWindow.setWidth(w.gridWidth/4);
            verWindow.setFill(Color.WHITE);
            verWindow.setStroke(Color.BLACK);
            getChildren().add(verWindow);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 3;

        }
        else if(Menu.state == "horDoor") {
            Rectangle horDoor = new Rectangle();
            horDoor.setHeight(w.gridHeight/3);
            horDoor.setWidth(w.gridWidth);
            horDoor.setFill(Color.BROWN);
            horDoor.setStroke(Color.BLACK);
            getChildren().add(horDoor);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 4;


        }
        else if(Menu.state == "verDoor") {
            Rectangle verDoor = new Rectangle();
            verDoor.setHeight(w.gridHeight);
            verDoor.setWidth(w.gridWidth/3);
            verDoor.setFill(Color.BROWN);
            verDoor.setStroke(Color.BLACK);
            getChildren().add(verDoor);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 4;


        }
        else if(Menu.state == "Tree") {
            Circle tree = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
            tree.setStroke(Color.BLACK);
            tree.setFill(Color.DARKGREEN);
            getChildren().add(tree);
            board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 5;
            //r.printBoard();
            //Uncomment above if you want to print the 2d array containing the objects, for some reason

        }
    }
}
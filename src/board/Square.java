package board;



import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Square extends StackPane {

    MainApp w = new MainApp();
    Properties p = new Properties();
   //ArrayList<Shapes> list = new ArrayList<Shapes>();
    public static boolean getCleared = false;
    List<Integer> objectProperties = new LinkedList<Integer>();


    public Square(double x, double y, double width, double height,boolean getCleared) {

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

    void pacmanPositioner(){

        Circle pacman = new Circle((987 / w.getgridWidth()), 654 / w.getgridHeight(), 9);
        pacman.setStroke(Color.LIGHTGREEN);
        Image icon = new Image("/icons/pacman.png");
        pacman.setFill(new ImagePattern(icon));

        Circle ghost = new Circle((3 / w.getgridWidth()), 3 / w.getgridHeight(), 9);
        ghost.setStroke(Color.LIGHTGREEN);
        Image icon2 = new Image("/icons/ghost.png");
        ghost.setFill(new ImagePattern(icon2));

        getChildren().addAll(pacman);
        getChildren().addAll(ghost);
    }


    void mousePressedOnSquare(MouseEvent e) {
        if(Menu.state == "Sentry") {
            System.out.println("SENTRY SELECTED");
            Circle sentry = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
            sentry.setStroke(Color.BLACK);
            sentry.setFill(Color.RED);
            getChildren().add(sentry);
        }
        else if(Menu.state == "Wall") {
            Rectangle Wall = new Rectangle();
            Wall.setHeight(w.gridHeight);
            Wall.setWidth(w.gridWidth);
            Wall.setFill(Color.GRAY);
            Wall.setStroke(Color.BLACK);
            getChildren().add(Wall);


        }
        else if(Menu.state == "horWindow") {

            Rectangle horWindow = new Rectangle();
            horWindow.setHeight(w.gridHeight/4);
            horWindow.setWidth(w.gridWidth);
            horWindow.setStroke(Color.BLACK);
            horWindow.setFill(Color.WHITE);
            getChildren().add(horWindow);

        }
        else if(Menu.state == "verWindow") {
            Rectangle verWindow = new Rectangle();
            verWindow.setHeight(w.gridHeight);
            verWindow.setWidth(w.gridWidth/4);
            verWindow.setFill(Color.WHITE);
            verWindow.setStroke(Color.BLACK);
            getChildren().add(verWindow);

        }
        else if(Menu.state == "horDoor") {
            Rectangle horDoor = new Rectangle();
            horDoor.setHeight(w.gridHeight/3);
            horDoor.setWidth(w.gridWidth);
            horDoor.setFill(Color.BROWN);
            horDoor.setStroke(Color.BLACK);
            getChildren().add(horDoor);
        }
        else if(Menu.state == "verDoor") {
            Rectangle verDoor = new Rectangle();
            verDoor.setHeight(w.gridHeight);
            verDoor.setWidth(w.gridWidth/3);
            verDoor.setFill(Color.BROWN);
            verDoor.setStroke(Color.BLACK);
            getChildren().add(verDoor);

        }
        else{System.out.println("FUCK");}
    }
}
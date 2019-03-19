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

public class Square extends StackPane {

    MainApp w = new MainApp();
    int sentryRange = 15;


    public Square(double x, double y, double width, double height) {

        // create rectangle
        Rectangle rectangle = new Rectangle(x, y, width, height);

        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.LIGHTGREEN);

        setOnMousePressed(e->mousePressedOnSquare(e));

        // set position
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(rectangle);
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
        //state = "Sentry";
        /**Here the String state should be obtained from the menu.
         * i.e. after clicking a button corresponding with an obstacle,
         * the value of state is passed. For now it is hardcoded.
         */
        if(Menu.state == "Sentry") {
            System.out.println("SENTRY SELECTED");
            Circle sentry = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
            sentry.setStroke(Color.BLACK);
            sentry.setFill(Color.RED);

            getChildren().add(sentry);
        }
        else if(Menu.state == "vertWall") {
            Rectangle vertWall = new Rectangle();
            /**Extra condition to see if you are closer to left or right side of square.
             * If you are closer to the left, place on the left.
             * If you are closer to the right, place on the right.
             */
        }
        else if(Menu.state == "horWall") {
            Rectangle horWall = new Rectangle();
            /** Same applies as above.
             * But this time check proximity to upper/lower bound.
             */
        }
        else if(Menu.state == "horWindow") {

            Rectangle horWindow = new Rectangle();
            horWindow.setHeight(w.gridHeight/4);
            horWindow.setWidth(w.gridWidth);
            horWindow.setStroke(Color.BLACK);
            horWindow.setFill(Color.WHITE);
            double currentSquareY = ((int) ((e.getSceneY()/ w.getgridHeight())) * (getHeight()-1));
            System.out.println("e.getsceneY() is " + e.getSceneY());
            System.out.println("w.getgridHeight() is " + w.getgridHeight());
            System.out.println("getHeight is " + getHeight());
            System.out.println("currentsquareY is " + currentSquareY);

            if((e.getSceneY()/w.getgridHeight()) * 20  < (currentSquareY/2) +5) {
                System.out.println("First statement works");
                System.out.println((e.getSceneY()/w.getgridHeight()) * 20 + "<" + ((currentSquareY)/(2) + 5));
                horWindow.setX((int) (e.getSceneX()/w.getgridWidth()));
                horWindow.setY((int) (e.getSceneY()/w.getgridHeight()));
                getChildren().add(horWindow);
            }
            else if((e.getSceneY()/w.getgridHeight()) * 20 >= (currentSquareY/2) + 5) {
                System.out.println("Second statement works");
                System.out.println((e.getSceneY()/w.getgridHeight()) * 20 + ">=" + ((currentSquareY)/(2) + 5));
                horWindow.setX((int) (e.getSceneX()/w.getgridWidth()));
                horWindow.setY((int) (e.getSceneY() - w.getgridHeight())/ (w.getgridHeight()) + 0.5 * w.getgridHeight());
                getChildren().add(horWindow);
            }
            else
                System.out.println("Nothing works. It's all fucked.");

        }
        else if(Menu.state == "verWindow") {
            Rectangle verWindow = new Rectangle();
            //Same checking
        }
        else if(Menu.state == "horDoor") {
            Rectangle horDoor = new Rectangle();
            //Doors can be placed up down left or right, unless bordered by walls.
        }
        else if(Menu.state == "verDoor") {
            Rectangle verDoor = new Rectangle();
        }
        else{System.out.println("FUCK");}
    }
}
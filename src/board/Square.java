package board;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Square extends StackPane {

    MainApp w = new MainApp();
    int sentryRange = 15;
    String state;

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

    void mousePressedOnSquare(MouseEvent e) {
        state = "Sentry";
        /**Here the String state should be obtained from the menu.
         * i.e. after clicking a button corresponding with an obstacle,
         * the value of state is passed. For now it is hardcoded.
         */
        if(state == "Sentry") {
            Circle sentry = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
            sentry.setStroke(Color.BLACK);
            sentry.setFill(Color.RED);

            getChildren().add(sentry);
        }
        else if(state == "vertWall") {
            Rectangle vertWall = new Rectangle();
            /**Extra condition to see if you are closer to left or right side of square.
             * If you are closer to the left, place on the left.
             * If you are clsoer to the right, place on the right.
             */
        }
        else if(state == "horWall") {
            Rectangle horWall = new Rectangle();
            /** Same applies as above.
             * But this time check proximity to upper/lower bound.
             */
        }
        else if(state == "horWindow") {
            Rectangle horWindow = new Rectangle();
            // Same checking
        }
        else if(state == "verWindow") {
            Rectangle verWindow = new Rectangle();
            //Same checking
        }
        else if(state == "horDoor") {
            Rectangle horDoor = new Rectangle();
            //Doors can be placed up down left or right, unless bordered by walls.
        }
        else if(state == "verDoor") {
            Rectangle verDoor = new Rectangle();
        }
    }

}
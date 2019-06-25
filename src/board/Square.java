package board;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import javafx.scene.input.MouseEvent;
import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Properties;

public class Square extends StackPane {

    MainApp w = new MainApp();
    Properties p = new Properties();
    public static boolean getCleared = false;
    public double targetX;
    public double targetY;
    public static Point targetVector = new Point();
    public static int [][] board= {
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2,0,0,0,3,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,4,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,32,2,32,2,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,2,32,2,2,2,2,32,2,2,2,2,2,2,0,0,0,0,0,0,0,0,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,5,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,2,32,2,2,2,2,2,2,32,2,2,42,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};
    public static boolean targetPlaced;

    public Square() {}

    /**
     * Constructor of square class. Responsible for placing all obstacles in their correct spots.
     * Every object (and their variations for doors and windows) corresponds to a number on the board.
     * Depending on what that number represents we draw the corresponding object on the board, and add it to the 2d matrix
     * @param x x coordinate of the object to be placed
     * @param y y coordinate of the object to be placed
     * @param width width of the object to be placed
     * @param height height of the object to be placed
     * @param getCleared boolean that checks whether we want to clear the board or not
     * @param ii secondary x coordinate corresponding to the 2d matrix
     * @param jj secondary y coordinate corresponding to the 2d matrix
     */
    public Square(double x, double y, double width, double height, boolean getCleared, int ii, int jj) {

        Rectangle rectangle = new Rectangle(x, y, width, height);
        Circle pSentry = new Circle(10);
        Rectangle background = new Rectangle(x, y, width, height);
        Circle pTree = new Circle(10);
        background.setFill(Color.LIGHTGREEN);
        background.setStroke(Color.BLACK);

        if (board[ii][jj]==0){
            rectangle.setFill(Color.LIGHTGREEN);
            rectangle.setStroke(Color.BLACK);
        }
        else if (board[ii][jj]==1){
            pSentry.setFill(Color.RED);
            pSentry.setStroke(Color.BLACK);
            pSentry.setCenterX(x + 0.5*w.gridHeight);
            pSentry.setCenterY(y + 0.5*w.gridHeight);
            pSentry.setRadius(10);
        }
        else if (board[ii][jj]==2){
            rectangle.setFill(Color.GRAY);
            rectangle.setStroke(Color.BLACK);
        }
        else if (board[ii][jj]==3){
            rectangle.setHeight(w.gridHeight/4);
            rectangle.setWidth(w.gridWidth);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStroke(Color.BLACK);
        }
        else if (board[ii][jj]==4){
            rectangle.setHeight(w.gridHeight/3);
            rectangle.setWidth(w.gridWidth);
            rectangle.setFill(Color.BROWN);
            rectangle.setStroke(Color.BLACK);
        }
        else if (board[ii][jj]==5){
            pTree.setCenterX(x + 0.5*w.getgridHeight());
            pTree.setCenterY(y + 0.5*w.getgridHeight());
            pTree.setFill(Color.GREEN);
            pTree.setStroke(Color.BLACK);
        }
        else if(board[ii][jj] == 42 ) {
            rectangle.setHeight(w.gridHeight);
            rectangle.setWidth(w.gridWidth/3);
            rectangle.setFill(Color.BROWN);
            rectangle.setStroke(Color.BLACK);
        }
        else if(board[ii][jj] == 32) {
            rectangle.setHeight(w.gridHeight);
            rectangle.setWidth(w.gridWidth/4);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStroke(Color.BLACK);
        }

        setOnMousePressed(e->mousePressedOnSquare(e));

        // set position
        setTranslateX(x);
        setTranslateY(y);
        getChildren().addAll(background);

        if (board[ii][jj]==1){

            getChildren().addAll(pSentry);
        }
        else if (board[ii][jj]==5){

            getChildren().addAll(pTree);
        }
        else  {

            getChildren().addAll(rectangle);
        }

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

    /**
     * Depending on what is selected in the drop down menu on the right side of the game,
     * we place the corresponding object on the same position where the mouse is currently pointed
     * @param e mouse-click keylistener
     */
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
        else if(Menu.state == "Intruder Target") {
            if(targetPlaced == false) {
                Circle intruderTarget = new Circle((e.getSceneX() / w.getgridWidth()), e.getSceneY() / w.getgridHeight(), 10);
                intruderTarget.setFill(Color.GOLD);
                intruderTarget.setStroke(Color.BROWN);
                getChildren().add(intruderTarget);
                board[(int) (e.getSceneX()/w.getgridWidth())][(int) (e.getSceneY()/w.getgridHeight())] = 6;
                targetPlaced = true;
                targetX = e.getSceneX();
                targetY = e.getSceneY();
            }
        }
    }

    /**
     * Method for returning the location of the target on the board. Used for the intruder bot
     * when checking for targets in its proximity, and changing direction.
     * @return targetVector a vector containing the coordinates of the target
     */
    public static Point getTargetCoordinates() {
        for(int i=0; i<50;i++) {
            for(int j=0;j<50;j++) {
                if(board[i][j] == 6) {
                    targetVector.setLocation(i,j);
                }
            }
        }
        return targetVector;
    }
}
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Square extends StackPane {

    MainApp w = new MainApp();
    Properties p = new Properties();
   //ArrayList<Shapes> list = new ArrayList<Shapes>();


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
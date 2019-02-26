import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Square extends StackPane {

    MainApp w = new MainApp();

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
        Circle circle = new Circle((e.getSceneX()/w.getgridWidth()), e.getSceneY()/w.getgridHeight(), 10);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.RED);
        getChildren().add(circle);


    }
}
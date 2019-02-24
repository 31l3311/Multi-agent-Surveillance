import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainApp extends Application {

    private double sceneWidth = 800;
    private double sceneHeight = 800;

    private int n = 50;
    private int m = 50;

    double gridWidth = sceneWidth / n;
    double gridHeight = sceneHeight / m;

    MyNode[][] playfield = new MyNode[n][m];


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setResizable(true);
        Group root = new Group();

        // initialize playfield
        for( int i=0; i < n; i++) {
            for( int j=0; j < m; j++) {

                // create node
                MyNode node = new MyNode( i * gridWidth, j * gridHeight, gridWidth, gridHeight);

                // add node to group
                root.getChildren().add( node);

                // add to playfield for further reference using an array
                playfield[i][j] = node;

            }
        }


        Scene scene = new Scene( root, sceneWidth, sceneHeight);

        primaryStage.setScene( scene);
        primaryStage.show();

    }

    public static class MyNode extends StackPane {

        public MyNode(double x, double y, double width, double height) {

            // create rectangle
            Rectangle rectangle = new Rectangle( width, height);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTBLUE);

            // set position
            setTranslateX(x);
            setTranslateY(y);

            getChildren().addAll(rectangle);

        }

    }

}
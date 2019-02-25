import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainApp extends Application {

    static double windowWidth = 1000;
    static double windowHeight = 1000;

    static int rowCells = 50;
    static int columnCells = 50;

    static double gridWidth = windowWidth / rowCells;
    static double gridHeight = windowHeight / columnCells;
    private MyNode[][] playfield = new MyNode[rowCells][columnCells];


    public static void main(String[] args) {
        launch(args);
    }

    static void mousePressedOnNode(MouseEvent e) {

       // System.out.println("mouse pressed on (x-y): "+e.getX()+"-"+e.getY());
        double roundedX = (int)(e.getSceneX()/gridWidth);
        double roundedY = (int)(e.getSceneY()/gridHeight);
        System.out.println("Node coordinate is: " + "(" + roundedX + ", " + roundedY + ")");
    }

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // initialize playfield
        for( int i=0; i < rowCells; i++) {
            for( int j=0; j < columnCells; j++) {
                // create node
                MyNode node = new MyNode( i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                // add node to group
                root.getChildren().add( node);
                // add to playfield for further reference using an array
                playfield[i][j] = node;

            }
        }
        Scene scene = new Scene( root, windowWidth+350, windowHeight);
        primaryStage.setScene( scene);
        primaryStage.show();
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
    }

    public static class MyNode extends StackPane {

        public MyNode(double x, double y, double width, double height) {

            // create rectangle
            Rectangle rectangle = new Rectangle(x, y, width, height);
            rectangle.setStroke(Color.BLACK);
            rectangle.setFill(Color.LIGHTGREEN);

            setOnMousePressed(e->mousePressedOnNode(e));

            // set position
            setTranslateX(x);
            setTranslateY(y);
            getChildren().addAll(rectangle);
        }
    }

}
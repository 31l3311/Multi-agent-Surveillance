import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
            for( int j=0; j < columnCells; j++) {
                // create node
                Square node = new Square( i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                // add node to group
                root.getChildren().add(node);
                // add to playfield for further reference using an array
                playfield[i][j] = node;
            }
        }
        Scene scene = new Scene( root, windowWidth+350, windowHeight);
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
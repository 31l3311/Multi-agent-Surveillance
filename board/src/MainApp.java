import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import static javafx.application.Application.launch;

public class MainApp {

    Stage window;

    public static void main (String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Multi Agent Surveillance");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));



        window.show();

    }
}

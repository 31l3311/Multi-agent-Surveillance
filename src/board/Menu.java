import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.awt.event.ActionListener;

public class Menu {

	public int menuWidth = 350;
	public static String state;
	MainApp mainApp = new MainApp();
	public static boolean getCleared = true;


	public Menu(Group root) {
		Group squares = root;
	}

	private void getChoice(ChoiceBox<String> chooseType) {
		String state = chooseType.getValue();
	}

	public VBox createMenu()
	{
		Button clearBoard = new Button("Clear board");
		ChoiceBox<String> chooseType = new ChoiceBox<>();
		chooseType.getItems().add("Sentry");
		chooseType.getItems().add("Wall");
		chooseType.getItems().add("verWindow");
		chooseType.getItems().add("horWindow");
		chooseType.getItems().add("horDoor");
		chooseType.getItems().add("verDoor");

		//Listening for selection changes
		chooseType.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> state = newValue);

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//mainApp.root.getChildren().remove(1, 49);
				for(int i=0; i<50; i++) {
					for(int j=0; j<50; j++) {
						Square square = new Square(i*20, j*20, 20, 20, getCleared);
					}
				}
			}
		};
		clearBoard.setOnAction(buttonHandler);

		VBox menu = new VBox();
		menu.getChildren().addAll(clearBoard,chooseType);
		menu.setAlignment(Pos.TOP_CENTER);
		menu.setSpacing(20);
		menu.setMinWidth(menuWidth);

		return menu;
	}


}
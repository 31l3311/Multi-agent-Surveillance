import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class Menu {

	public int menuWidth = 350;
	public static String state;


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
		chooseType.getItems().add("vertWall");
		chooseType.getItems().add("horWall");
		chooseType.getItems().add("verWindow");
		chooseType.getItems().add("horWindow");
		chooseType.getItems().add("horDoor");
		chooseType.getItems().add("verDoor");

		//Listening for selection changes
		chooseType.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> state = newValue);

		VBox menu = new VBox();

		menu.getChildren().addAll(clearBoard,chooseType);
 		menu.setAlignment(Pos.TOP_CENTER);
 		menu.setSpacing(20);
 		menu.setMinWidth(menuWidth);
	
 		return menu;
	}
	
	
}
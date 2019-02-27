package board;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class Menu{
	public int menuWidth = 350;
	public String state;
	
	public Menu(Group root) {
		Group squares = root;
	}
	
	public VBox createMenu()
	{
		VBox menu = new VBox();
 			Button clearBoard = new Button("Clear board");
 			ChoiceBox chooseType = new ChoiceBox(FXCollections.observableArrayList("Building", "Sentry tower", "Tree"));
 		menu.getChildren().addAll(clearBoard,chooseType);
 		menu.setAlignment(Pos.TOP_CENTER);
 		menu.setSpacing(20);
 		menu.setMinWidth(menuWidth);
	
 		return menu;
	}
	
	
}
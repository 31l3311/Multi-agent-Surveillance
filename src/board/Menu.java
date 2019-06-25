package board;

import com.sun.prism.paint.Color;
import javafx.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import java.awt.*;


public class Menu {

	public int menuWidth = 350;
	public static String state;
	MainApp mainApp = new MainApp();
	public static boolean getCleared = true;


	/**
	 * Constructor for the menu class
	 * @param root object
	 */
	public Menu(Group root) {
		Group squares = root;
	}


	/**
	 * Method meant to put all of the elements together and create the menu
	 * @return object of VBox returns a complete menu
	 */
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
		chooseType.getItems().add("Tree");
		chooseType.getItems().add("Intruder Target");

		//Listening for selection changes
		chooseType.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> state = newValue);

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {

			/**
			 * Method intented to handle events
			 * @param event object
			 */
			@Override
			public void handle(ActionEvent event) {
				//mainApp.root.getChildren().remove(1, 49);
				for(int i=0; i<50; i++) {
					for(int j=0; j<50; j++) {
						Square square = new Square(i*20, j*20, 20, 20, getCleared, i, j);
					}
				}
			}
		};
		clearBoard.setOnAction(buttonHandler);

		VBox menu = new VBox();

 			//Button clearBoard = new Button("Clear board");
 			//ChoiceBox chooseType = new ChoiceBox(FXCollections.observableArrayList("Building", "Sentry tower", "Tree"));
 			Button deployAgents = new Button("Deploy Agents");
 			Button deployIntruders = new Button("Deploy Intruders");
 			Button quit = new Button("Quit");
			Button start = new Button("Start");

 			quit.setOnAction(new EventHandler<ActionEvent>() {
				/**
				 * Eventhandler which listens to the Clear Bord button
				 * @param event object
				 */
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});

 			clearBoard.setOnAction(new EventHandler<ActionEvent>() {
				/**
				 * Eventhandler which listens to the Deploy Agents button
				 * @param event object
				 */
				@Override
				public void handle(ActionEvent event) {

				}
			});

 			deployAgents.setOnAction(new EventHandler<ActionEvent>() {
				/**
				 * Eventhandler which listens to the Deploy Intruders button
				 * @param event object
				 */
				@Override
				public void handle(ActionEvent event) {
					//agent1 = new SurveillanceAgent( new Point(10,10));

				}
			});

 			deployIntruders.setOnAction(new EventHandler<ActionEvent>() {
				/**
				 * Eventhandler which listens to the start button
				 * @param event object
				 */
				@Override
				public void handle(ActionEvent event) {
					//agent2 = new Intruder( new Point(50, 50));

				}
			});

			start.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					//run = new Run(Square.board);
					MainApp.startTimer();
					for(int i = 0; i<mainApp.surveillanceAgents.size(); i++) {
						MainApp.surveillanceAgents.get(i).setRadius(10);
					}
					for(int i = 0; i<mainApp.intruders.size(); i++) {
						MainApp.intruders.get(i).setRadius(10);
					}
//					MainApp.circle.setRadius(10);
//					MainApp.circle1.setRadius(10);
				}
			});

 		menu.getChildren().addAll(clearBoard,chooseType, deployAgents, deployIntruders, start, quit);

 		menu.setAlignment(Pos.TOP_CENTER);
 		menu.setSpacing(20);
 		menu.setMinWidth(menuWidth);

		return menu;
	}
	
	
}
package gui;

//@author Sofia

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gameDatabase.DataBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable{
	
	private DataBase thisDB = new DataBase();
	
	@FXML
	private Label status;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	// if user clicks on "Select a game to run" button, the Select Game window will open
	public void selectGame(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide menu window (stage)
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/SelectGame.fxml").openStream());
			SelectGameController sgController = (SelectGameController)loader.getController();
			sgController.setDatabase(thisDB);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Select a Game");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// if user clicks on the "start the game" button, the Start Game window will open
	public void startGame(ActionEvent event) {
		if (thisDB.getGames().size() == 0){ // Check if user has selected a game before starting one
			status.setText("You must select a game to run before starting!");
		}
		else {
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide menu window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/StartGame.fxml").openStream());
				StartGameController stgController = (StartGameController)loader.getController();
				stgController.setDatabase(thisDB);
				stgController.runRace(thisDB);
				Scene scene = new Scene(root);
				primaryStage.setTitle("Start the Game");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// if user clicks on the "Display the final results of all games" button, the Display Results window will open with a table of results 
	// from all the games
	public void displayResults(ActionEvent event) throws ClassNotFoundException {
		if (thisDB.getGames().isEmpty() == true){
			status.setText("No games to display!");
		}
		else { 
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide login window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/DisplayResults.fxml").openStream());
				DisplayResultsController drController = (DisplayResultsController)loader.getController();
				drController.setDatabase(thisDB);
				drController.displayAllResults(thisDB);
				Scene scene = new Scene(root);
				primaryStage.setTitle("Display All Results");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// if the user clicks on the "Display the points of all games" button, the Display Points window will open with a table of 
	// the total points for all the athletes
	public void displayPoints(ActionEvent event) {
		if (thisDB.getGames().isEmpty() == true) {
			status.setText("No games to display!");
		}
		else { 
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide login window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/DisplayPoints.fxml").openStream());
				DisplayPointsController dpController = (DisplayPointsController)loader.getController();
				dpController.setDatabase(thisDB);
				dpController.displayAllPoints(thisDB);
				Scene scene = new Scene(root);
				primaryStage.setTitle("Display Total Points");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// if the user clicks on the "Exit" button, the program will be exited
	public void exit(ActionEvent event) {
		Platform.exit(); // exits from JavaFX
		System.exit(0); // closes app
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

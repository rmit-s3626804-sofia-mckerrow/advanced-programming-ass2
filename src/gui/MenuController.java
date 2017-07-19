package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gameComponents.Game;
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
	Game myGame;
	
	@FXML
	private Label status;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	public void selectGame(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide menu window (stage)
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/SelectGame.fxml").openStream());
			SelectGameController sgController = (SelectGameController)loader.getController();
			sgController.setDatabase(thisDB);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
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
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// check for races and print formatted list of race results
	public void displayResults(ActionEvent event) {
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
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void displayPoints() {
		if (thisDB.getGames().isEmpty() == true) {
			status.setText("No games to display!");
		}
	}
	
	public void exit(ActionEvent event) {
		Platform.exit(); // exits from JavaFX
		System.exit(0); // closes app
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

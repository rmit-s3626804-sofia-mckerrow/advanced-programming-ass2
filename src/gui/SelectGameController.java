package gui;

//@author Sofia

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gameComponents.Game;
import gameDatabase.DataBase;
import gui.SelectAthletesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SelectGameController implements Initializable{
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private String raceType;
	private ArrayList<Game> games = new ArrayList<Game>();
	
	@FXML
	private Label addGameStatus;
	
	@FXML
	private Label raceID;
	
	@FXML
	private Button next;

	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	// add a cycling game
	public void selectCycling(ActionEvent event) {
		raceType = "cycle";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Cycling game has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
	}

	// add a running game
	public void selectRunning(ActionEvent event) {
		raceType = "run";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Running game has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
	}

	// add a swimming game
	public void selectSwimming(ActionEvent event) {
		raceType = "swim";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Swimming game has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
	}

	public void nextButtonClick(ActionEvent event) throws ClassNotFoundException, SQLException {
		if (thisDB.getGames().size() > 0) { // check if a game has been added
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide SelectGame window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/SelectAthletes.fxml").openStream());
				SelectAthletesController saController = (SelectAthletesController)loader.getController();
				saController.addAthletesToList(thisDB);
				saController.setDatabase(thisDB);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			addGameStatus.setText("Please select a game");
		}
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}
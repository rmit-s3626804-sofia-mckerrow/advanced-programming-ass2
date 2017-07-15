package gui;

import java.io.IOException;
import java.net.URL;
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
	
	public DataBase getThisDB(DataBase thisDB) {
		return thisDB;
	}
		
	public void selectCycling(ActionEvent event) {
		raceType = "cycle";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Cycling race has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
		next.setVisible(true);
	}

	public void selectRunning(ActionEvent event) {
		raceType = "run";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Running race has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
		next.setVisible(true);
	}

	public void selectSwimming(ActionEvent event) {
		raceType = "swim";
		myGame = thisDB.addRace(raceType);
		addGameStatus.setText("Swimming race has been added");
		raceID.setText("Race ID: " + myGame.getRaceID());
		next.setVisible(true);
	}

	public void nextButtonClick(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide login window (stage)
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
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customExceptions.GameFullException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class SelectAthletesController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	
	@FXML
	public ListView<String> athletesList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addAthletesToList(thisDB);
		athletesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	public void addAthletesToList(DataBase thisDB) {
		myGame = thisDB.getLastGame();
		ArrayList<Athlete> thisRaceAthletes = thisDB.getAthletes();
		
		for(int i = 0; i < thisRaceAthletes.size(); i++){
			Athlete thisAthlete = thisRaceAthletes.get(i);
			if (thisAthlete.canRaceInGame(myGame))
				athletesList.getItems().add(thisAthlete.getName());
		}
		
		ObservableList<String> names;
		names = athletesList.getSelectionModel().getSelectedItems(); // get the selected names
		
		for (String name : names) {
			System.out.println(name);
		}
	}
	
	public void selectAthletes() {
		
	}
	
	public void addAthleteToRace() throws GameFullException {
		
	}

}

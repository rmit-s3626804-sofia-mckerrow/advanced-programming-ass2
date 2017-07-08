package gui;

import java.net.URL;
import java.util.ResourceBundle;

import customExceptions.GameFullException;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.fxml.Initializable;

public class SelectAthletesController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void selectAthletes() {
		
	}
	
	public void addAthleteToRace() throws GameFullException {
		
	}

}

package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.omg.CORBA.PUBLIC_MEMBER;

import customExceptions.GameFullException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public class SelectAthletesController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	
	@FXML
	public ListView<Athlete> athletesList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// addAthletesToList(thisDB);
		athletesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	public void addAthletesToList(DataBase thisDB) {
		myGame = thisDB.getLastGame();
		
		ArrayList<Athlete> athletes = thisDB.initialiseAthletesList();
		ArrayList<Athlete> athletesToSelectList = new ArrayList<Athlete>();
		
		for(int i = 0; i < athletes.size(); i++){
			Athlete thisAthlete = athletes.get(i);
			if (thisAthlete.canRaceInGame(myGame))
				athletesToSelectList.add(thisAthlete);
		}
		for (int i = 0; i < athletesToSelectList.size(); i++) {
			System.out.println(athletesToSelectList.get(i).getID() + " " + athletesToSelectList.get(i).getName() + " " + athletesToSelectList.get(i).getType());
		}
		ObservableList<Athlete> names = FXCollections.observableArrayList(athletesToSelectList);
		athletesList.setItems(names);
		
		athletesList.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() {
			
			@Override
			public ListCell<Athlete> call(ListView<Athlete> param) {
				
				ListCell<Athlete> cell = new ListCell<Athlete>(){
					
					@Override
					public void updateItem(Athlete a, boolean empty) {
						super.updateItem(a, empty);
						if (a != null) {
							setText(a.getName());
						}
					}
				};
				
				return cell;
				
			}
		});
		
	}
	
	public void selectAthletes() {
		
	} 
	
	public void addAthleteToRace() throws GameFullException {
		
	}
	
	public void removeAthleteFromRace() {
		
	}

}

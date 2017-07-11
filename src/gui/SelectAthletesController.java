package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customExceptions.GameFullException;
import customExceptions.TooFewAthleteException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public class SelectAthletesController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private ObservableList<Athlete> athletesListNames, raceAthletesNames;
	private ArrayList<Athlete> athletesToSelectList = new ArrayList<Athlete>();
	private ArrayList<Athlete> thisRaceAthletes = new ArrayList<Athlete>();
	
	@FXML
	public ListView<Athlete> athletesList, raceAthletes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// addAthletesToList(thisDB);
		athletesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	public void addAthletesToList(DataBase thisDB) {
		myGame = thisDB.getLastGame();
		
		ArrayList<Athlete> athletes = thisDB.initialiseAthletesList();
				
		for(int i = 0; i < athletes.size(); i++){
			Athlete thisAthlete = athletes.get(i);
			if (thisAthlete.canRaceInGame(myGame))
				athletesToSelectList.add(thisAthlete);
		}
		
		athletesListNames = FXCollections.observableArrayList(athletesToSelectList);
		athletesList.setItems(athletesListNames);
		
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
	
	public void addAthleteToRace(ActionEvent event) {
		Athlete raceAthlete;		
		raceAthlete = athletesList.getSelectionModel().getSelectedItem();
		thisRaceAthletes.add(raceAthlete);
		
		for (int i = 0; i < thisRaceAthletes.size(); i++) {
			System.out.println(thisRaceAthletes.get(i).getID() + " " + thisRaceAthletes.get(i).getName() + " " + thisRaceAthletes.get(i).getType());
		}
		
		raceAthletesNames = FXCollections.observableArrayList(thisRaceAthletes);
		raceAthletes.setItems(raceAthletesNames);
		
		raceAthletes.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() {
			
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
		
		int selectedItem = athletesList.getSelectionModel().getSelectedIndex();
		athletesListNames.remove(selectedItem);
	}
	
	public void removeAthleteFromRace(ActionEvent event) throws TooFewAthleteException, GameFullException {
		int selectedItem = athletesList.getSelectionModel().getSelectedIndex();
		athletesListNames.remove(selectedItem);
	}

}

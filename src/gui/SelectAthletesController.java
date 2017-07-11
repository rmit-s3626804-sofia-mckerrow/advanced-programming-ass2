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
	private ArrayList<Athlete> athletesToSelectList = new ArrayList<Athlete>(); // array list of athletes for user to select from
	private ArrayList<Athlete> thisRaceAthletes = new ArrayList<Athlete>(); // array list of athletes selected for race
	private ObservableList<Athlete> athletesListNames; // observable list of athletes for user to select from
	private ObservableList<Athlete> raceAthletesNames; // observable list of athletes selected for race
	
	@FXML
	public ListView<Athlete> athletesList, raceAthletes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// addAthletesToList(thisDB);
		athletesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	// add athletes to list for user to select athletes for race
	public void addAthletesToList(DataBase thisDB) {
		myGame = thisDB.getLastGame();
		
		ArrayList<Athlete> athletes = thisDB.initialiseAthletesList(); // get all the athletes in the database
				
		for(int i = 0; i < athletes.size(); i++){
			Athlete thisAthlete = athletes.get(i);
			if (thisAthlete.canRaceInGame(myGame)) // if athlete can race in the selected game, add them to the array list
				athletesToSelectList.add(thisAthlete);
		}
		
		// set the array list of athletes to the athletesList listview
		athletesListNames = FXCollections.observableArrayList(athletesToSelectList);
		athletesList.setItems(athletesListNames); 
		
		// display the list of athletes on the athletesList listview
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
	
	// add the selected athlete to the race
	public void addAthleteToRace(ActionEvent event) {
		Athlete raceAthlete = athletesList.getSelectionModel().getSelectedItem(); // get the selected athlete
		thisRaceAthletes.add(raceAthlete); // add the selected athlete to the array list of athletes for the race
		myGame.setAthletesForRace(thisRaceAthletes);
		
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++){
			System.out.println((i+1) + ". " + myGame.getRaceAthletes().get(i).getName());
		}
		
		/*for (int i = 0; i < thisRaceAthletes.size(); i++) {
			System.out.println(thisRaceAthletes.get(i).getID() + " " + thisRaceAthletes.get(i).getName() + " " + thisRaceAthletes.get(i).getType());
		}*/
		
		raceAthletesNames = FXCollections.observableArrayList(thisRaceAthletes);
		raceAthletes.setItems(raceAthletesNames); // set the array list of race athletes to the raceAthletes listview
		
		raceAthletes.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() { // display the list of race athletes on the raceAthletes listview
			
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
		
		athletesToSelectList.remove(raceAthlete); // remove the selected athlete from the list of athletes to select from
		int selectedItem = athletesList.getSelectionModel().getSelectedIndex();
		athletesListNames.remove(selectedItem); // remove the selected athlete from the displayed list of athletes to select from
	}
	
	// remove the selected athlete from the race
	public void removeAthleteFromRace(ActionEvent event) throws TooFewAthleteException, GameFullException {
		Athlete raceAthlete = raceAthletes.getSelectionModel().getSelectedItem(); // get the selected athlete
		thisRaceAthletes.remove(raceAthlete); // remove the selected athletes from the array list of race athletes
		myGame.setAthletesForRace(thisRaceAthletes);
		
		raceAthletesNames = FXCollections.observableArrayList(thisRaceAthletes);
		raceAthletes.setItems(raceAthletesNames); // set the array list of race athletes to the raceAthletes listview
		
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++){
			System.out.println((i+1) + ". " + myGame.getRaceAthletes().get(i).getName());
		}
		
		raceAthletes.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() { // display the list of race athletes on the raceAthletes listview
					
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
		
		athletesToSelectList.add(raceAthlete); // add the removed athlete back to the list of athletes to select from
		athletesListNames = FXCollections.observableArrayList(athletesToSelectList);
		athletesList.setItems(athletesListNames); // set the array list of athletes to the athletesList listview				
		athletesList.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() { // display the list of athletes on the athletesList listview
					
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

}

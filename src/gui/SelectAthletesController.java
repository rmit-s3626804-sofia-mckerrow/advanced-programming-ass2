package gui;

//@author Sofia

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customExceptions.GameFullException;
import customExceptions.TooFewAthleteException;
import customExceptions.WrongTypeException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SelectAthletesController implements Initializable {
	
	DataBase thisDB;
	Game myGame;
	private ArrayList<Athlete> athletesToSelectList = new ArrayList<Athlete>(); // array list of athletes for user to select from
	private ArrayList<Athlete> thisRaceAthletes = new ArrayList<Athlete>(); // array list of athletes selected for race
	private ObservableList<Athlete> athletesListNames; // observable list of athletes for user to select from
	private ObservableList<Athlete> raceAthletesNames; // observable list of athletes selected for race
	
	@FXML
	public ListView<Athlete> athletesList, raceAthletes;
	
	@FXML
	private Label status;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		athletesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	// add athletes to list for user to select athletes for race
	public void addAthletesToList(DataBase thisDB) throws ClassNotFoundException, SQLException {
		myGame = thisDB.getLastGame();
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		
		if (thisDB.doesDatabaseExist()) {
			thisDB.initialiseParticipantsListFromDatabase(); // get all the athletes in the database
			athletes = thisDB.getAthletes();
		}
		else if (thisDB.canParticipantsFileBeFound()) {
			try {
				thisDB.readParticipantsFromFile();
				athletes = thisDB.getAthletes();
			} catch (FileNotFoundException e) {
				System.out.println("File could not be found");
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < athletes.size(); i++){
			Athlete thisAthlete = athletes.get(i);
			try {
				if (thisAthlete.canRaceInGame(myGame)) // if athlete can race in the selected game, add them to the array list
					athletesToSelectList.add(thisAthlete);
			} catch (WrongTypeException e) {
				status.setText("Cannot add a participant to a wrong type of game");
			}
		}
				
		// set the array list of athletes to the athletesList listview
		athletesListNames = FXCollections.observableArrayList(athletesToSelectList);
		athletesList.setItems(athletesListNames);
		
		setCellFactoryForList(athletesList); // display the list of athletes on the athletesList listview		
	}
	
	// add the selected athlete to the race
	public void addAthleteToRace(ActionEvent event) throws GameFullException {
		status.setText("");
		
		try {
			myGame.checkIfRaceHasMax(myGame); // check if there are the maximum number of athletes in the game
			
			Athlete raceAthlete = athletesList.getSelectionModel().getSelectedItem(); // get the selected athlete
			
			if (raceAthlete != null) { // check if an athlete has been selected
				thisRaceAthletes.add(raceAthlete); // add the selected athlete to the array list of athletes for the race
				myGame.setAthletesForRace(thisRaceAthletes);
			
				raceAthletesNames = FXCollections.observableArrayList(thisRaceAthletes);
				raceAthletes.setItems(raceAthletesNames); // set the array list of race athletes to the raceAthletes listview
				
				setCellFactoryForList(raceAthletes); // display the list of race athletes on the raceAthletes listview
			
				athletesToSelectList.remove(raceAthlete); // remove the selected athlete from the list of athletes to select from
				int selectedItem = athletesList.getSelectionModel().getSelectedIndex();
				athletesListNames.remove(selectedItem); // remove the selected athlete from the displayed list of athletes to select from
				
				setCellFactoryForList(athletesList); // display the list of athletes to select from on the athletesList listview
				
			}
			
		} catch (GameFullException e) {
			status.setText("Game is full, cannot add more than 8 athletes");
		}
		
	}
	
	// remove the selected athlete from the race
	public void removeAthleteFromRace(ActionEvent event) throws TooFewAthleteException, GameFullException {
		status.setText("");
		Athlete raceAthlete = raceAthletes.getSelectionModel().getSelectedItem(); // get the selected athlete
		
		if (raceAthlete != null) { // check if an athlete has been selected
			thisRaceAthletes.remove(raceAthlete); // remove the selected athletes from the array list of race athletes
			myGame.setAthletesForRace(thisRaceAthletes);
		
			raceAthletesNames = FXCollections.observableArrayList(thisRaceAthletes);
			raceAthletes.setItems(raceAthletesNames); // set the array list of race athletes to the raceAthletes listview
		
			setCellFactoryForList(raceAthletes);
		
			athletesToSelectList.add(raceAthlete); // add the removed athlete back to the list of athletes to select from
			athletesListNames = FXCollections.observableArrayList(athletesToSelectList);
			athletesList.setItems(athletesListNames); // set the array list of athletes to the athletesList listview				
			
			setCellFactoryForList(athletesList);
		}
	}
	
	// display the array list on the listview
	public void setCellFactoryForList(ListView<Athlete> list) {
		list.setCellFactory(new Callback<ListView<Athlete>, ListCell<Athlete>>() { 
			
			@Override
			public ListCell<Athlete> call(ListView<Athlete> param) {
					
				ListCell<Athlete> cell = new ListCell<Athlete>(){
						
					@Override
					public void updateItem(Athlete a, boolean empty) {
						super.updateItem(a, empty);
						if (empty || a == null) {
							setText(null);
							setGraphic(null);
						}
						else {
							setText(a.getName());
						}
					}
				};						
				return cell;						
			}
		});	
	}
	
	public void nextButtonClick(ActionEvent event) throws ClassNotFoundException, SQLException {
		status.setText("");
		int min = myGame.getMinAthletes();
		
		try {
			myGame.checkIfRaceHasMin(myGame); // check if there are the minimum number of athletes in the game
		} catch (TooFewAthleteException e) {
			status.setText("Not enough athletes to start game, need at least 4");
		}
		
		if (myGame.getRaceSize() >= min) { // check if there are enough athletes before next window appears		
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide SelectAthletes window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/SelectOfficial.fxml").openStream());
				SelectOfficialController soController = (SelectOfficialController)loader.getController();
				soController.addOfficialsToList(thisDB);
				soController.setDatabase(thisDB);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}
}



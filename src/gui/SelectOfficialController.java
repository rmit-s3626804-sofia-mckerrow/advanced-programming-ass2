package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;
import gameComponents.Athlete;
import gameComponents.Game;
import gameComponents.Official;
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

public class SelectOfficialController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private ArrayList<Official> officialToSelectList = new ArrayList<Official>(); // array list of officials for user to select from
	private ObservableList<Official> officialsListNames; // observable list of officials for user to select from
	
	@FXML
	public ListView<Official> officialsList;
	
	@FXML
	private Label status, officialStatus;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		officialsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	// add officials to list for user to select athletes for race
	public void addOfficialsToList(DataBase thisDB) {
		myGame = thisDB.getLastGame();
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++){
			System.out.println(myGame.getRaceAthletes().get(i).getName());
		}
		
		ArrayList<Official> officials = thisDB.initialiseOfficialsList(); // get all the athletes in the database
				
		for(int i = 0; i < officials.size(); i++){
			Official thisOfficial = officials.get(i);
			officialToSelectList.add(thisOfficial); // add official to array list
		}
		
		for (int i = 0; i < officials.size(); i++) {
			System.out.println(officials.get(i).getID() + " " + officials.get(i).getName() + " " + officials.get(i).getType());
		}
		
		// set the array list of officials to the officialsList listview
		officialsListNames = FXCollections.observableArrayList(officialToSelectList);
		officialsList.setItems(officialsListNames);
		
		setCellFactoryForList(officialsList); // display the list of officials on the athletesList listview		
	}
	
	public void addOfficialToRace(ActionEvent event) {
		
	}
	
	// add the selected athlete to the race
	/*public void addAthleteToRace(ActionEvent event) throws GameFullException {
		status.setText("");
		
		try {
			myGame.checkIfRaceHasMax(myGame); // check if there are the maximum number of athletes in the game
			
			Athlete raceAthlete = athletesList.getSelectionModel().getSelectedItem(); // get the selected athlete
			
			if (raceAthlete != null) { // check if an athlete has been selected
				thisRaceAthletes.add(raceAthlete); // add the selected athlete to the array list of athletes for the race
				myGame.setAthletesForRace(thisRaceAthletes);
			
				for (int i = 0; i < myGame.getRaceAthletes().size(); i++){
					System.out.println((i+1) + ". " + myGame.getRaceAthletes().get(i).getName());
				}
			
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
		
	}*/
	
	// display the array list on the listview
	public void setCellFactoryForList(ListView<Official> list) {
		list.setCellFactory(new Callback<ListView<Official>, ListCell<Official>>() { 
			
			@Override
			public ListCell<Official> call(ListView<Official> param) {
					
				ListCell<Official> cell = new ListCell<Official>(){
						
					@Override
					public void updateItem(Official o, boolean empty) {
						super.updateItem(o, empty);
						if (empty || o == null) {
							setText(null);
							setGraphic(null);
						}
						else {
							setText(o.getName());
						}
					}
				};						
				return cell;						
			}
		});	
	}
	
	public void nextButtonClick(ActionEvent event) {
		status.setText("");
		
		try {
			myGame.checkIfRaceHasOfficial(myGame); // check if there are the minimum number of athletes in the game
		} catch (NoRefereeException e) {
			status.setText("No official selected for race");
		}
		
		/*try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide login window (stage)
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/gui/SelectOfficial.fxml").openStream());
			SelectAthletesController saController = (SelectAthletesController)loader.getController();
			saController.addAthletesToList(thisDB);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}

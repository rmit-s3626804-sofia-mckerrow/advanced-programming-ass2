package gui;

//@author Sofia

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customExceptions.NoRefereeException;
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
	
	private DataBase thisDB = new DataBase();
	private Game myGame;
	private ArrayList<Official> officials = new ArrayList<Official>(); // array list of officials for user to select from
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
	public void addOfficialsToList(DataBase thisDB) throws ClassNotFoundException, SQLException {
		myGame = thisDB.getLastGame();
		
		if (thisDB.canDatabaseFileBeFound()) {
			officials = thisDB.getOfficials();
		}
		else if (thisDB.canParticipantsFileBeFound()) {
			try {
				thisDB.readParticipantsFromFile();
				officials = thisDB.getOfficials();
			} catch (FileNotFoundException e) {
				System.out.println("File could not be found");
				e.printStackTrace();
			}
		}
		
		// set the array list of officials to the officialsList listview
		officialsListNames = FXCollections.observableArrayList(officials);
		officialsList.setItems(officialsListNames);
		
		setCellFactoryForList(officialsList); // display the list of officials on the athletesList listview		
	}
	
	// add the selected official to the race
	public void addOfficialToRace(ActionEvent event) {
		status.setText("");
				
		Official raceOfficial = officialsList.getSelectionModel().getSelectedItem(); // get the selected official
			
		if (raceOfficial != null && myGame.getRaceOfficial() == null) { // check if an official has been selected
			myGame.setOfficialForRace(raceOfficial); // add the selected official to the race
			
			officialStatus.setText("Race Official selected: " + myGame.getRaceOfficial().getName());
			
			officials.remove(raceOfficial); // remove the selected official from the list of officials to select from
			officialsListNames = FXCollections.observableArrayList(officials);
			officialsList.setItems(officialsListNames); // set the array list of race athletes to the raceAthletes listview
				
			setCellFactoryForList(officialsList); // display the list of race athletes on the raceAthletes listview		
		}
		else if (myGame.getRaceOfficial() != null) {
			status.setText("An official has already been selected for the game");
		}
		else {
			status.setText("Please select an official to add to the game");
		}	
		
	}
	
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
			myGame.checkIfRaceHasOfficial(myGame); // check if there is an official for the game
		} catch (NoRefereeException e) {
			status.setText("No official selected for game");
		}
		
		if (myGame.getRaceOfficial() != null) {
			try {
				((Node)event.getSource()).getScene().getWindow().hide(); // hide SelectOfficial window (stage)
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/gui/Menu.fxml").openStream());
				MenuController mController = (MenuController)loader.getController();
				mController.setDatabase(thisDB);
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

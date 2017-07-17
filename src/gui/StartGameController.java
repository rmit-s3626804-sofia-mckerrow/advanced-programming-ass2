package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StartGameController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private ObservableList<Athlete> raceResultsList; // observable list of race results
	
	@FXML
	private Label resultsTitle;
	
	@FXML
	private TableView<Athlete> resultsTable;
	
	@FXML
	private TableColumn<Athlete, String> athleteNames;
	
	@FXML
	private TableColumn<Athlete, Double> athleteTimes;
	
	@FXML
	private TableColumn<Athlete, Integer> athletePoints;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	public void runRace(DataBase thisDb) {
		myGame = thisDB.getLastGame();
		resultsTitle.setText("Results for Race " + myGame.getRaceID());
		
		myGame.competeAthletes(); // get race times
		myGame.getRaceOfficial().sortRace(thisDB); // sort the results from lowest to highest times - if there is a tie official decides
		myGame.getRaceOfficial().givePointsToWinners(thisDB); 	// give points to top three places
		
		// print results of race
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++){ 		
			System.out.println((i+1) + ". " + myGame.getRaceAthletes().get(i).getName() + " " + myGame.getRaceAthletes().get(i).getRoundTime() + 
					" " + myGame.getRaceAthletes().get(i).getRoundPoints());
			}
		
		ArrayList<Athlete> lastGameResults = myGame.getRaceAthletes();
		raceResultsList = FXCollections.observableArrayList(lastGameResults); // set raceResults as an observable list of the race results
		
		athleteNames.setCellValueFactory(new PropertyValueFactory<Athlete, String>("name"));
		athleteTimes.setCellValueFactory(new PropertyValueFactory<Athlete, Double>("roundTime"));
		athletePoints.setCellValueFactory(new PropertyValueFactory<Athlete, Integer>("roundPoints"));
		resultsTable.setItems(raceResultsList);
		// resultsTable.getColumns().addAll(athleteNames, times, points);	
		
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

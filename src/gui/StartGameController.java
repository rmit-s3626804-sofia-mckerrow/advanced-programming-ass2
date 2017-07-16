package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gameComponents.Athlete;
import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class StartGameController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private ObservableList<String> athletesIDs; // observable list of athletes' ids
	private ObservableList<Athlete> athletesNames; // observable list of athletes' names
	private ObservableList<Double> athletesTimes; // observable list of athletes' times
	private ObservableList<Integer> athletesPoints; // observable list of athletes' points
	
	@FXML
	private Label resultsTitle;
	
	@FXML
	private TableView<Athlete> resultsTable;
	
	@FXML
	private TableColumn<Athlete, String> athleteID;
	
	@FXML
	private TableColumn<Athlete, String> athleteName;
	
	@FXML
	private TableColumn<Athlete, String> time;
	
	@FXML
	private TableColumn<Athlete, String> points;
	
	

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
			System.out.println((i+1) + ". " + myGame.getRaceAthletes().get(i).getName() + " " + myGame.getRaceAthletes().get(i).getRoundTime() + " " +
				myGame.getRaceAthletes().get(i).getRoundPoints());
		}
		System.out.println();
		
		
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
	public void initialize(URL location, ResourceBundle resources) {
		athleteNames.setStyle("-fx-alignment: CENTER;");
		athleteTimes.setStyle("-fx-alignment: CENTER;");
		athletePoints.setStyle("-fx-alignment: CENTER;");
	}
	
	public void runRace(DataBase thisDB) {
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
		
		athleteNames.setCellValueFactory(new PropertyValueFactory<Athlete, String>("name")); // set the athletes' names in the athleteNames column
		athleteTimes.setCellValueFactory(new PropertyValueFactory<Athlete, Double>("roundTime")); // set the athletes' times in the athleteTimes column
		athletePoints.setCellValueFactory(new PropertyValueFactory<Athlete, Integer>("roundPoints")); // set the athletes' points in the athletePoints column
		resultsTable.setItems(raceResultsList);
		
		try {
			thisDB.recordLastGame(); // record the game results to the database
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void returnToMenuButtonClick(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide StartGame window (stage)
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
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

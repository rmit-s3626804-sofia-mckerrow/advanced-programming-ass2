package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gameComponents.Athlete;
import gameComponents.Game;
import gameComponents.Official;
import gameDatabase.DataBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DisplayResultsController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;
	private ObservableList<Game> resultsList;

	@FXML
	private TableView<Athlete> resultsTable;
	
	@FXML
	private TableColumn<Game, String> raceIDs;
	
	@FXML
	private TableColumn<Official, String> officialNames;
	
	@FXML
	private TableColumn<Athlete, String> athleteNames;
	
	@FXML
	private TableColumn<Athlete, Double> athleteTimes;
	
	@FXML
	private TableColumn<Athlete, Integer> athletePoints;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	public void displayAllResults(DataBase thisDB) {
		
	}
	
	public void returnToMenuButtonClick(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide DisplayResults window (stage)
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

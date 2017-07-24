package gui;

//@author Sofia

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gameDatabase.DataBase;
import gameDatabase.GameResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DisplayResultsController implements Initializable {
	
	private DataBase thisDB = new DataBase();
	private ArrayList<GameResult> gameResults = new ArrayList<GameResult>();
	private ObservableList<GameResult> resultsList;

	@FXML
	private TableView<GameResult> resultsTable;
	
	@FXML
	private TableColumn<GameResult, String> gameID;
	
	@FXML
	private TableColumn<GameResult, String> athleteID;
	
	@FXML
	private TableColumn<GameResult, String> athleteTime;
	
	@FXML
	private TableColumn<GameResult, String> athletePoints;
	
	@FXML
	private TableColumn<GameResult, String> officialID;
	
	@FXML
	private TableColumn<GameResult, String> date;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameID.setStyle("-fx-alignment: CENTER;");
		athleteID.setStyle("-fx-alignment: CENTER;");
		athleteTime.setStyle("-fx-alignment: CENTER;");
		athletePoints.setStyle("-fx-alignment: CENTER;");
		officialID.setStyle("-fx-alignment: CENTER;");
		date.setStyle("-fx-alignment: CENTER;");
	}
	
	// display the results of all the games in the results table
	public void displayAllResults(DataBase thisDB) throws ClassNotFoundException {
		ResultSet rs;
		
		try {
			if (thisDB.canDatabaseFileBeFound()) {
				rs = thisDB.getResultsFromDatabase();
				while (rs.next()) {
					GameResult gameResultItem = new GameResult(rs.getString("gameID"), rs.getString("athleteID"), 
							rs.getString("result"), rs.getString("score"), rs.getString("officialID"), rs.getString("date"));
					gameResults.add(gameResultItem);
				}
			}
			else if (thisDB.canParticipantsFileBeFound()) {
				ArrayList<GameResult> resultsArray = thisDB.getResults();
				for (int i = 0; i < resultsArray.size(); i++) {
					GameResult gameResultItem = new GameResult(resultsArray.get(i).getGameID(), resultsArray.get(i).getAthleteID(),
							resultsArray.get(i).getTime(), resultsArray.get(i).getPoints(), resultsArray.get(i).getOfficialID(),
							resultsArray.get(i).getDate());
					gameResults.add(gameResultItem);
				}				
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		resultsList = FXCollections.observableArrayList(gameResults);
		gameID.setCellValueFactory(new PropertyValueFactory<GameResult, String>("gameID"));
		athleteID.setCellValueFactory(new PropertyValueFactory<GameResult, String>("athleteID"));
		athleteTime.setCellValueFactory(new PropertyValueFactory<GameResult, String>("time"));
		athletePoints.setCellValueFactory(new PropertyValueFactory<GameResult, String>("points"));
		officialID.setCellValueFactory(new PropertyValueFactory<GameResult, String>("officialID"));
		date.setCellValueFactory(new PropertyValueFactory<GameResult, String>("date"));
		resultsTable.setItems(resultsList);
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
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Main Menu");
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

package gui;

//@author Sofia

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gameComponents.Athlete;
import gameDatabase.DataBase;
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

public class DisplayPointsController implements Initializable {
	
	DataBase thisDB = new DataBase();
	private ArrayList<Athlete> athletesPoints = new ArrayList<Athlete>();
	private ObservableList<Athlete> pointsList;
	
	@FXML
	private TableView<Athlete> pointsTable;
	
	@FXML
	private TableColumn<Athlete, String> athleteID;
	
	@FXML
	private TableColumn<Athlete, String> athleteName;
	
	@FXML
	private TableColumn<Athlete, Integer> totalPoints;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		athleteID.setStyle("-fx-alignment: CENTER;");
		athleteName.setStyle("-fx-alignment: CENTER;");
		totalPoints.setStyle("-fx-alignment: CENTER;");
	}
	
	// display the total points of all the athletes in the points table
	public void displayAllPoints(DataBase thisDB) {
		athletesPoints = thisDB.getAthletes();	
		pointsList = FXCollections.observableArrayList(athletesPoints);
		
		athleteID.setCellValueFactory(new PropertyValueFactory<Athlete, String>("id"));
		athleteName.setCellValueFactory(new PropertyValueFactory<Athlete, String>("name"));
		totalPoints.setCellValueFactory(new PropertyValueFactory<Athlete, Integer>("totalPoints"));
		totalPoints.setSortType(TableColumn.SortType.DESCENDING); // sort table in descending order of total points
		pointsTable.setItems(pointsList);
		pointsTable.getSortOrder().add(totalPoints);
	}
	
	public void returnToMenuButtonClick(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide DisplayPoints window (stage)
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

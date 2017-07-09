package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gameDatabase.DataBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable{
	
	private MenuModel menuModel = new MenuModel();
	private DataBase thisDB = new DataBase();
	
	@FXML
	private Label isConnected;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (menuModel.isDbConnected()) {
			isConnected.setText("Database is connected");
			thisDB.initialiseAthletesList(); // initialise arraylist of Athletes from database
			thisDB.initialiseOfficialsList(); // initialise arraylist of Officials from database
		}
		else {
			isConnected.setText("Database is not connected");
		}		
	}
	
	public void selectGame(ActionEvent event) {
		try {
			((Node)event.getSource()).getScene().getWindow().hide(); // hide login window (stage)
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = loader.load(getClass().getResource("/gui/SelectGame.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void startGame() {
		
	}
	
	public void displayResults() {
		
	}
	
	public void displayPoints() {
		
	}
	
	public void exit(ActionEvent event) {
		Platform.exit(); // exits from JavaFX
		System.exit(0); // closes app
	}

}

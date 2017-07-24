package gui;

//@author Sofia
	
import java.io.IOException;
import java.sql.SQLException;

import gameDatabase.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Ozlympic extends Application {
	DataBase thisDB = new DataBase();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			if (thisDB.canDatabaseFileBeFound()) {
				System.out.println("Database file has been found");
				try {
					thisDB.emptyResultsTable();
					thisDB.emptyResultsFile();
					thisDB.initialiseParticipantsListFromDatabase();
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
			}
			else if (thisDB.canParticipantsFileBeFound()) {
				System.out.println("Database file has not been found");
				System.out.println("Participants.txt file has been found");
				try {
					thisDB.emptyResultsFile();
					thisDB.readParticipantsFromFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Database is not connected");
				System.out.println("Participants.txt file could not be found");
				System.out.println("Game could not be started");
				System.exit(0);
			}
			
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource("/gui/Menu.fxml").openStream());
			MenuController mController = (MenuController)loader.getController();
			mController.setDatabase(thisDB);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Main Menu");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

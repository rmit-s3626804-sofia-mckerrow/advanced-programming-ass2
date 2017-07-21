package gui;
	
import java.sql.SQLException;

import gameDatabase.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Ozlympic extends Application {
	DataBase thisDB = new DataBase();
	Stage primaryStage = new Stage();
	
	public void run() {
		if (thisDB.isDbConnected()) {
			System.out.println("Database is connected");
			try {
				thisDB.emptyResults();
				start(primaryStage);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Database is not connected");
		}	
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Menu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

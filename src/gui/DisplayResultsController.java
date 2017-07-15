package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.fxml.Initializable;

public class DisplayResultsController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

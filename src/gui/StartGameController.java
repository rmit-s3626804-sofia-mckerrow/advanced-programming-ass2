package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gameComponents.Game;
import gameDatabase.DataBase;
import javafx.fxml.Initializable;

public class StartGameController implements Initializable {
	
	DataBase thisDB = new DataBase();
	Game myGame;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
	}
	
	public void runRace(DataBase thisDb) {
		myGame = thisDB.getLastGame();
		myGame.competeAthletes(); // get race times
		myGame.getRaceOfficial().sortRace(thisDB); // sort the results from lowest to highest times - if there is a tie official decides
		
		// print results of race
		System.out.println("   Name			Time (seconds)");
		for (int i = 0; i < myGame.getRaceAthletes().size(); i++){ 		
			System.out.print((i+1) +". ");
			System.out.printf("%-20s %8s %n", myGame.getRaceAthletes().get(i).getName(), myGame.getResultArray().get(i));
		}
		System.out.println();
		
		myGame.getRaceOfficial().givePointsToWinners(thisDB); 	// give points to top three places
	}
	
	public void setDatabase(DataBase thisDB) {
		this.thisDB = thisDB;
	}

}

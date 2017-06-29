package gameComponents;
//@author Sofia

import customExceptions.TooFewAthleteException;
import gameComponents.Game;
import gameDatabase.DataBase;

public class Official extends Participant {
	
	// Constructor for Official class
	public Official(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}
	
	// check if race has at least 4 athletes competing
	public void checkIfRaceHasMin(Game game) throws TooFewAthleteException{ 
		int min = game.getMinAthletes();
		int numberOfAthletesInRace = game.getRaceSize();
		if (numberOfAthletesInRace < min) {
			throw new TooFewAthleteException("Race cancelled, insufficient number of athletes for race");
		}
		
		/* if (numberOfAthletesInRace >= min) {
			return true;
		}
		return false;*/
	}
	
	// bubble sort - iterate through array and if next value is greater then swap, moves lowest number to the start of array
	public void sortRace(DataBase db){ 
		double temp; 			// temporary int for swapping
		Athlete tempA; 			// temporary athlete for swapping
		Game sort = db.getLastGame();
		for (int i = 0; i < sort.getResultArray().size(); i++){
			for (int j = 0; j < (sort.getResultArray().size() - 1); j++){
				int k = j + 1;
				if (sort.getResultArray().get(j) > sort.getResultArray().get(k)){
					// if j > j+1 swap athlete
					tempA = sort.getRaceAthletes().get(j);
					sort.getRaceAthletes().set(j, (sort.getRaceAthletes().get(k)));
					sort.getRaceAthletes().set(k, tempA);
					// swap time of athlete
					temp = sort.getResultArray().get(j);
					sort.getResultArray().set(j, (sort.getResultArray().get(k)));
					sort.getResultArray().set(k, temp);
				}
			}
		}
	}
	
	// give points to athletes in top three places in race
	public void givePointsToWinners(DataBase db){
		Game lastGame = db.getLastGame();
		int raceSize = db.getLastGame().getRaceAthletes().size();
		// position points and total points
		int first = 5;
		int firstTotal = lastGame.getRaceAthletes().get(0).getTotalPoints();
		int second = 3;
		int secondTotal = lastGame.getRaceAthletes().get(1).getTotalPoints();
		int third = 1;
		int thirdTotal = lastGame.getRaceAthletes().get(2).getTotalPoints();
		// give points
		for (int i = 0; i < raceSize; i++){
			if (i==0) {
				lastGame.getRaceAthletes().get(i).setRoundPoints(first);
				lastGame.getRaceAthletes().get(0).setTotalPoints(firstTotal += first);
			}
			if (i==1) {
				lastGame.getRaceAthletes().get(i).setRoundPoints(second);
				lastGame.getRaceAthletes().get(1).setTotalPoints(secondTotal += second);
			}
			if (i==2) {
				lastGame.getRaceAthletes().get(i).setRoundPoints(third);
				lastGame.getRaceAthletes().get(2).setTotalPoints(thirdTotal += third);
			}
			if (i > 2) lastGame.getRaceAthletes().get(i).setRoundPoints(0);
		}
	}
	
}

package gameComponents;

//@author Calvin (methods from Assignment 1) and Sofia

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;

public abstract class Game {
	
	private String raceID;
	private String raceType;
	private int minAthletes;
	private int maxAthletes;
	private double minRaceTime;
	private double maxRaceTime;
	private ArrayList<Athlete> raceAthletes;
	private ArrayList<Double> resultArray = new ArrayList<Double>();
	private ArrayList<Integer> pointsArray = new ArrayList<Integer>();
	private Official raceOfficial;
	private String date;
	
	public Game(String raceID) {
		this.raceID = raceID;
	}

	public String getRaceID() {
		return raceID;
	}
	
	public String getRaceType() {
		return raceType;
	}

	public int getMinAthletes() {
		return minAthletes;
	}
	
	public int getMaxAthletes() {
		return maxAthletes;
	}

	public double getMinRaceTime() {
		return minRaceTime;
	}
	
	public double getMaxRaceTime() {
		return maxRaceTime;
	}
	
	public ArrayList<Athlete> getRaceAthletes() {
		return raceAthletes;
	}
	
	public ArrayList<Double> getResultArray() {
		return resultArray;
	}
	
	public ArrayList<Integer> getPointsArray() {
		return pointsArray;
	}

	public Official getRaceOfficial() {
		return raceOfficial;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setMinAthletes(int minAthletes) {
		this.minAthletes = minAthletes;
	}
	
	public void setMaxAthletes(int maxAthletes) {
		this.maxAthletes = maxAthletes;
	}

	public void setMinRaceTime(double minRaceTime) {
		this.minRaceTime = minRaceTime;
	}

	public void setMaxRaceTime(double maxRaceTime) {
		this.maxRaceTime = maxRaceTime;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	// static method for generating a race id
	public static String getNextID(String raceType, int id){
		String nextID;
		if (id < 9){
			nextID = (raceType.substring(0, 1)) + "0" +  (id + 1);
		} else {
			nextID = (raceType.substring(0, 1)) + (id + 1);
		}
		return nextID;
	}
	
	public void setAthletesForRace(ArrayList<Athlete> thisRaceAthletes) {
		raceAthletes = thisRaceAthletes;
	}
	
	public Official setOfficialForRace(Official thisRaceOfficial) {
		raceOfficial = thisRaceOfficial;
		
		return raceOfficial;
	}
	
	// iterate through the athletes and call their compete() method
	public void competeAthletes(){ 					
		if (resultArray.size() == 0){ // check if race has been raced
			for (Athlete raceAthlete: raceAthletes) {
				// pass current game to access min and max race times of subclass
				double result = Athlete.compete(this);
				raceAthlete.setRoundTime(result);
				resultArray.add(result);
			}
		}
		else {
			// to race again, clear the results arraylist and compete again
			resultArray.clear();
			for (int i = 0; i < raceAthletes.size(); i++){
				double result = Athlete.compete(this);
				resultArray.add(result);
			}
		}
		Date d = new Date();	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setDate(df.format(d));
	}

	public int getRaceSize(){
		int raceSize = 0;
		if (raceAthletes != null) {
			raceSize = raceAthletes.size();
		}
		return raceSize;
	}
	
	// check if race has the minimum number of athletes competing
	public void checkIfRaceHasMin(Game game) throws TooFewAthleteException{ 
		int min = game.getMinAthletes();
		int numberOfAthletesInRace = game.getRaceSize();
		if (numberOfAthletesInRace < min) {
			throw new TooFewAthleteException("Not enough athletes to start game, need at least 4");
		}
	}
	
	// check if race has the maximum number of athletes competing
	public void checkIfRaceHasMax(Game game) throws GameFullException{ 
		int max = game.getMaxAthletes();
		int numberOfAthletesInRace = game.getRaceSize();
		if (numberOfAthletesInRace == max) {
			throw new GameFullException("Game is full, cannot add more than 8 athletes");
		}
	}
	
	// check if race has an official
	public void checkIfRaceHasOfficial (Game game) throws NoRefereeException {
		Official checkOfficial = game.getRaceOfficial();
		if (checkOfficial == null) {
			throw new NoRefereeException("No official selected for game");
		}
	}
}

package gameComponents;
//@author Calvin

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import customExceptions.GameFullException;
import customExceptions.NoRefereeException;
import customExceptions.TooFewAthleteException;
import gameDatabase.DataBase;

public abstract class Game {
	
	private String raceID;
	private String raceType;
	private int minAthletes;
	private int maxAthletes;
	private double minRaceTime;
	private double maxRaceTime;
	private Athlete userPrediction;
	private ArrayList<Athlete> raceAthletes;
	private ArrayList<Double> resultArray = new ArrayList<Double>();
	private ArrayList<Integer> pointsArray = new ArrayList<Integer>();
	private Official raceOfficial;
	private String date;
	private boolean isOn = false;

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
	
	public void setPointsArray(ArrayList<Integer> pointsArray) {
		this.pointsArray = pointsArray;
	}

	public ArrayList<Integer> getPointsArray() {
		return pointsArray;
	}

	public Official getRaceOfficial() {
		return raceOfficial;
	}
	
	public Athlete getUserPrediction() {
		return userPrediction;
	}

	public void setRaceType(String raceType) {
		this.raceType = raceType;
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
	
	public void setUserPrediction(Athlete userPrediction) {
		this.userPrediction = userPrediction;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public static String getNextID(String raceType, int id){
		// static method for generating a race id
		// would need to change if another race with same first letter was implemented
		String nextID;
		if (id < 9){
			nextID = (raceType.substring(0, 1)) + "0" +  (id + 1);
		} else {
			nextID = (raceType.substring(0, 1)) + (id + 1);
		}
		return nextID;
	}
	
	public void setRandomValidAthletesForRace(DataBase db) throws GameFullException { 	
		// find up to maxAthletes who can participate in that race type
		ArrayList<Athlete> shuffledAthletes = db.getShuffledAthletes();
		ArrayList<Athlete> thisRaceAthletes = new ArrayList<Athlete>();
		
		for(int i = 0; i < shuffledAthletes.size(); i++){
			Athlete thisAthlete = shuffledAthletes.get(i);
			if (thisAthlete.canRaceInGame(this)) thisRaceAthletes.add(thisAthlete);
			if (thisRaceAthletes.size() == 8) break;
			else if (thisRaceAthletes.size() > 8) { // testing
				throw new GameFullException("Game is full, cannot add more than 8 athletes");
			}
		}
		raceAthletes = thisRaceAthletes;
	}
	
	public void setAthletesForRace(ArrayList<Athlete> thisRaceAthletes) {
		raceAthletes = thisRaceAthletes;
	}
	
	public Official setOfficialForRace(Official thisRaceOfficial) {
		raceOfficial = thisRaceOfficial;
		
		return raceOfficial;
	}
	
	public void competeAthletes(){ 					
		// iterate through the athletes and call their compete() method
		// check if race has been raced
		if (resultArray.size() == 0){
			for (int i = 0; i < raceAthletes.size(); i++){
				// pass current game to access min and max race times of subclass
				double result = Athlete.compete(this);	
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

	public Official getOfficialForRace(DataBase db){
		// generate an official for the race
		Random r = new Random();
		int index =  r.nextInt(db.getOfficials().size());
		raceOfficial = db.getOfficials().get(index);
		return raceOfficial;
	}
	
	// check if race has minimum amount of athletes
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
			throw new TooFewAthleteException("Race cancelled, insufficient number of athletes for race");
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
			throw new NoRefereeException("Race cancelled, no official selected for race");
		}
	}
}

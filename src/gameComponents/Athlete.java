package gameComponents;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

//@author Calvin

public class Athlete extends Participant{

	private int roundPoints;
	private int totalPoints;
	private double roundTime;
	
	// Constructor for Athlete class
	public Athlete(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
		roundPoints = 0;
		totalPoints = 0;
		roundTime = 0;
	}
	
	public int getRoundPoints() {
		return roundPoints;
	}

	public void setRoundPoints(int roundPoints) {
		this.roundPoints = roundPoints;
	}
	
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	public double getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(double roundTime) {
		this.roundTime = roundTime;
	}
	
	public static double compete(Game game) { // get values from Game subclasses and return the raceTime
		double raceTime;
		double min = game.getMinRaceTime();
		double max = game.getMaxRaceTime();
		Random r = new Random();
		raceTime = min + (max - min) * r.nextDouble();
		BigDecimal bd = new BigDecimal(raceTime).setScale(1, RoundingMode.HALF_EVEN);
		raceTime = bd.doubleValue();
		return raceTime;
	}
	
	public boolean canRaceInGame(Game game){ // check if an athlete can enter a race - generic athlete cannot race
		return false;
	}
}

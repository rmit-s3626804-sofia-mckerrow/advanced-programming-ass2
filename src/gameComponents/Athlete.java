package gameComponents;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import customExceptions.WrongTypeException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

//@author Calvin

public class Athlete extends Participant{

	private SimpleIntegerProperty roundPoints;
	private SimpleIntegerProperty totalPoints;
	private SimpleDoubleProperty roundTime;
	
	// Constructor for Athlete class
	public Athlete(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
		roundPoints = new SimpleIntegerProperty(0);
		totalPoints = new SimpleIntegerProperty(0);
		roundTime = new SimpleDoubleProperty(0);
	}
	
	public int getRoundPoints() {
		return roundPoints.get();
	}

	public void setRoundPoints(int roundPoints) {
		this.roundPoints = new SimpleIntegerProperty(roundPoints);
	}
	
	public IntegerProperty roundPointsProperty() {
		return roundPoints;
	}
	
	public int getTotalPoints() {
		return totalPoints.get();
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = new SimpleIntegerProperty(totalPoints);
	}
	
	public IntegerProperty totalPointsProperty() {
		return totalPoints;
	}
	
	public double getRoundTime() {
		return roundTime.get();
	}

	public void setRoundTime(double roundTime) {
		this.roundTime = new SimpleDoubleProperty(roundTime);
	}
	
	public DoubleProperty roundTimeProperty() {
		return roundTime;
	}
	
	public boolean canRaceInGame(Game game) throws WrongTypeException { // check if an athlete can enter a race - generic athlete cannot race
		return false;
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
	
	
}

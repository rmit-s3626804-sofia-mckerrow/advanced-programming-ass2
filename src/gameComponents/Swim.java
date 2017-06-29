package gameComponents;
//@author Sofia

public class Swim extends Game {
	
	// Constructor for Swim class
	public Swim (String raceID) {
		super(raceID);
		this.setMinAthletes(4);	// minimum number of athletes for a swimming race is 4
		this.setMaxAthletes(8); // maximum number of athletes for a swimming race is 8
		this.setMinRaceTime(100.00); // minimum time for a swimming race is 100 seconds
		this.setMaxRaceTime(200.00); // maximum time for a swimming race is 200 seconds
	}
}

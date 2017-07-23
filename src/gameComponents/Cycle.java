package gameComponents;
//@author Sofia

public class Cycle extends Game {

	public Cycle(String raceID) {
		super(raceID);
		this.setMinAthletes(4);	// minimum number of athletes for a cycling race is 4
		this.setMaxAthletes(8);	// maximum number of athletes for a cycling race is 8
		this.setMinRaceTime(500.00); // minimum time for a cycling race is 500 seconds
		this.setMaxRaceTime(800.00); // maximum time for a cycling race is 500 seconds
	}
}

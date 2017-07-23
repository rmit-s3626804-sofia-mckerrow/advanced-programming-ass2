package gameComponents;

//@author Sofia

public class Run extends Game {

	public Run (String raceID) {
		super(raceID);
		this.setMinAthletes(4);	// minimum number of athletes for a running race is 4
		this.setMaxAthletes(8); // maximum number of athletes for a running race is 8
		this.setMinRaceTime(10.00); // minimum time for a running race is 10 seconds
		this.setMaxRaceTime(20.00); // maximum time for a running race is 20 seconds
	}
}

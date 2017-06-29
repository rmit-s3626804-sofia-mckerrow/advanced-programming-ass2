package gameComponents;
//@author Sofia

public class SuperAthlete extends Athlete {
	
	// Constructor for SuperAthlete class
	public SuperAthlete(String id, String name, String athleteType, int age, String state) {
		super(id, name, athleteType, age, state);
	}

	// check if an athlete can enter a race
	public boolean canRaceInGame(Game game) {	
		return true;
	}

}

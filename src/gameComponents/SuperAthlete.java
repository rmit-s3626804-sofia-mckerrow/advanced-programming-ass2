package gameComponents;

//@author Sofia

import customExceptions.WrongTypeException;

public class SuperAthlete extends Athlete {
	
	public SuperAthlete(String id, String name, String athleteType, int age, String state) {
		super(id, name, athleteType, age, state);
	}

	// check if a SuperAthlete can enter a race
	public boolean canRaceInGame(Game game) throws WrongTypeException {	
		return true;
	}

}

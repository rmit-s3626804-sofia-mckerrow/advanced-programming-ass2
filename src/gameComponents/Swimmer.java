package gameComponents;

//@author Sofia

import customExceptions.WrongTypeException;

public class Swimmer extends Athlete {
	
	public Swimmer(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}

	// check if a swimmer can enter a race
	public boolean canRaceInGame(Game game) throws WrongTypeException {	
		if (game instanceof Swim) {
			return true;
		}
		return false;
	}

}

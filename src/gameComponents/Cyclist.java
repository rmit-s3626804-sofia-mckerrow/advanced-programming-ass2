package gameComponents;

//@author Sofia

import customExceptions.WrongTypeException;

public class Cyclist extends Athlete {
	
	public Cyclist(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}

	// check if a Cyclist can enter a race
	public boolean canRaceInGame(Game game) throws WrongTypeException {	
		if (game instanceof Cycle) {
			return true;
		}
		return false;
	}
	
}

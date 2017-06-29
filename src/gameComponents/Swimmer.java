package gameComponents;
//@author Sofia

public class Swimmer extends Athlete {
	
	// Constructor for Swimmer class
	public Swimmer(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}

	// check if a swimmer can enter a race
	public boolean canRaceInGame(Game game) {	
		if (game instanceof Swim) {
			return true;
		}
		return false;
	}

}

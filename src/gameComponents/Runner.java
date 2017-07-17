package gameComponents;

//@author Sofia

public class Runner extends Athlete {
	
	// Constructor for Runner class
	public Runner(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}

	// check if a runner can enter a race
	public boolean canRaceInGame(Game game) {	
		if (game instanceof Run) {
			return true;
		}
		return false;
	}

}

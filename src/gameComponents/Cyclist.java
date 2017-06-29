package gameComponents;
//@author Sofia

public class Cyclist extends Athlete {
	
	// Constructor for Cyclist class
	public Cyclist(String id, String name, String type, int age, String state) {
		super(id, name, type, age, state);
	}

	// check if a Cyclist can enter a race
	public boolean canRaceInGame(Game game) {	
		if (game instanceof Cycle) {
			return true;
		}
		return false;
	}
	
}

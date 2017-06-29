package customExceptions;
// @author Sofia

public class GameFullException extends Exception {
	
	public GameFullException(String errMsg) {
		super(errMsg);
		System.out.println(errMsg); // testing
	}

}

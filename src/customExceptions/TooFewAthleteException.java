package customExceptions;
//@author Sofia

public class TooFewAthleteException extends Exception {
	
	public TooFewAthleteException(String errMsg) {
		super(errMsg);
		// System.out.println(errMsg); // testing
	}

}

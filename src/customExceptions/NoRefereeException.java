package customExceptions;
//@author Sofia

public class NoRefereeException extends Exception {
	
	public NoRefereeException(String errMsg) {
		super(errMsg);
		System.out.println(errMsg); // testing
	}

}

package customExceptions;
// @author Sofia

public class WrongTypeException extends Exception {
	
	public WrongTypeException(String errMsg) {
		super(errMsg);
		System.out.println(errMsg); // testing
	}

}

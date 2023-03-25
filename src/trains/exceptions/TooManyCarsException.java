package trains.exceptions;

public class TooManyCarsException extends Exception {
	public TooManyCarsException() {
		super("Can't add more cars");
	}
}

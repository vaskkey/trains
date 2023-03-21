package trains.exceptions;

public class CarTooHeavyException extends Exception {
    public CarTooHeavyException() {
        super("This car is too heavy for this train");
    }
}

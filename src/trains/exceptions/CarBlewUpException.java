package trains.exceptions;

public class CarBlewUpException extends Exception {
    public CarBlewUpException(int id) {
        super(String.format("Car #%d blew up the whole train", id));
    }
}

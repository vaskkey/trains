package trains.exceptions;

public class ToxinSpilledException extends Exception {
    public ToxinSpilledException(int id) {
        super(String.format("Car #%d spilled toxic waste", id));
    }
}
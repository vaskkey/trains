package route.exceptions;

public class LastStationException extends Exception {
    public LastStationException() {
        super("Train arrived to the last station");
    }
}

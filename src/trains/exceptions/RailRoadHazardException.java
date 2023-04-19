package trains.exceptions;

public class RailRoadHazardException extends Exception {
    public RailRoadHazardException() {
        super("train is going too fast");
    }
}

package trains.exceptions;

public class CarSpilledException extends Exception {
    public CarSpilledException(int id) {
        super(String.format("Car #%d spilled its contents", id));
    }
}

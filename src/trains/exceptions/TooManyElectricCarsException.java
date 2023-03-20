package trains.exceptions;

public class TooManyElectricCarsException  extends Exception{
    public TooManyElectricCarsException() {
        super("Can't add more electric cars");
    }
}

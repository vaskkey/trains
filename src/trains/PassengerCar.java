package trains;

public class PassengerCar extends TrainCar {
    protected int seatsCount;

    public PassengerCar(int seatsCount) {
        super(true, 20);
        this.seatsCount = seatsCount;
    }

    public PassengerCar() {
        this(52);
    }

}

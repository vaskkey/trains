package trains;

import java.util.Random;
import java.util.Scanner;

public class PassengerCar extends TrainCar {
    protected int seatsCount;
    private int currentPassengers;

    public PassengerCar(int seatsCount) {
        super(true, 20);
        this.seatsCount = seatsCount;
    }

    public PassengerCar() {
        this(52);
    }

    public static PassengerCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("How many seats?");
        int seats = Integer.parseInt(scanner.nextLine());

        if (seats <= 0) {
            seats = 52;
        }

        return new PassengerCar(seats);
    }

    @Override
    public String toString() {
        return String.format("Passenger Car #%d. Seats: %d. Passengers: %d", this.id, this.seatsCount, this.currentPassengers);
    }

    @Override
    public void stationAction() throws Exception {
        Random rand = new Random();

        this.currentPassengers = rand.nextInt(this.seatsCount);
    }

    @Override
    public void endRouteAction() {
        this.currentPassengers = 0;
    }
}

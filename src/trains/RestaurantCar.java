package trains;

import java.util.Random;
import java.util.Scanner;

public class RestaurantCar extends TrainCar {
    static String[] DISHES = {"chicken", "fish", "broccoli"};
    protected int seatsCount;
    private String mainDish = DISHES[0];

    public RestaurantCar(int seatsCount) {
        super(true, 18);
        this.seatsCount = seatsCount;
    }

    public RestaurantCar() {
        this(10);
    }

    public static RestaurantCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("How many seats?");
        int seats = Integer.parseInt(scanner.nextLine());

        return new RestaurantCar(seats);
    }

    @Override
    public String toString() {
        return String.format("Restaurant Car #%d. Seats: %d. Currently serving: %s", this.id, this.seatsCount, this.mainDish);
    }

    @Override
    public void stationAction() throws Exception {
        Random rand = new Random();

        this.mainDish = DISHES[rand.nextInt(DISHES.length)];
    }

    @Override
    public void endRouteAction() {
        this.mainDish = "chicken";
    }
}
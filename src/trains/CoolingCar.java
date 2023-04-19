package trains;

import java.util.Random;
import java.util.Scanner;

public class CoolingCar extends FreightCar {
    String cargo;

    int temperature;

    public CoolingCar(String cargo) {
        super(true, 26);
        this.cargo = cargo;
    }

    public static CoolingCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What is the cargo?");
        String type = scanner.nextLine();

        return new CoolingCar(type);
    }

    @Override
    public void stationAction() {
        Random rand = new Random();
        this.temperature = rand.nextInt(-10, 10);
    }

    @Override
    public void endRouteAction() {
        super.endRouteAction();
        this.temperature = 0;
    }

    @Override
    public String toString() {
        return String.format("Cooling Car #%d. Cargo: %s. Temperature: %d", this.id, this.cargo, this.temperature);
    }
}

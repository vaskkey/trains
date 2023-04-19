package trains;

import trains.exceptions.CarBlewUpException;

import java.util.Random;
import java.util.Scanner;

public class ExplosivesCar extends HeavyFreightCar {
    String explosivesType;

    public ExplosivesCar(String explosivesType) {
        super(40);
        this.explosivesType = explosivesType;
    }

    public static ExplosivesCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What is the explosives type?");
        String type = scanner.nextLine();

        return new ExplosivesCar(type);
    }

    @Override
    public String toString() {
        return String.format("Explosives Car #%d. Explosives: %s. Current weight: %f", this.id, this.explosivesType, this.cargoWeight);
    }

    @Override
    public void stationAction() throws Exception {
        super.stationAction();
        Random rand = new Random();
        int i = rand.nextInt(100);

        if (i == 1) {
            throw new CarBlewUpException(this.id);
        }
    }
}

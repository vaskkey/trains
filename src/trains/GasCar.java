package trains;

import java.util.Scanner;

public class GasCar extends FreightCar {
    String gasType;

    public GasCar(String gasType) {
        super(24);
        this.gasType = gasType;
    }

    public static GasCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What is the gas type?");
        String type = scanner.nextLine();

        return new GasCar(type);
    }

    @Override
    public String toString() {
        return String.format("Gas Car #%d. Gas: %s", this.id, this.gasType);
    }
}

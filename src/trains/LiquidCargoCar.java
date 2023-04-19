package trains;

import trains.exceptions.CarSpilledException;

import java.util.Random;
import java.util.Scanner;

public class LiquidCargoCar extends FreightCar implements ILiquidCar {
    int litres;

    public LiquidCargoCar(int litres) {
        super();
        this.litres = litres;
    }

    public static LiquidCargoCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("How many litres can this car fit? (will default to 20)");
        int litres = Integer.parseInt(scanner.nextLine());

        if (litres <= 0) {
            litres = 20;
        }

        return new LiquidCargoCar(litres);
    }

    @Override
    public int getLitreCapacity() {
        return this.litres;
    }

    @Override
    public boolean didSpill() {
        Random rand = new Random();
        int i = rand.nextInt(100);
        return i < 5;
    }

    @Override
    public String toString() {
        return String.format("Liquid Cargo Car #%d. Litres: %d", this.id, this.litres);
    }

    @Override
    public void stationAction() throws Exception {
        super.stationAction();

        if (this.didSpill()) {
            throw new CarSpilledException(this.id);
        }
    }
}

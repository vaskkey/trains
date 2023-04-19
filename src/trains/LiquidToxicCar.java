package trains;

import trains.exceptions.ToxinSpilledException;

import java.util.Random;
import java.util.Scanner;

public class LiquidToxicCar extends ToxicCar implements ILiquidCar {
    private final int litres;

    public LiquidToxicCar(int litres, Toxicity toxicity) {
        super(toxicity);
        this.litres = litres;
    }

    public static LiquidToxicCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("How many litres can this car fit? (will default to 20)");
        int litres = Integer.parseInt(scanner.nextLine());

        if (litres <= 0) {
            litres = 20;
        }

        System.out.println("What is the toxicity level? HIGH, MEDIUM, LOW (default)");
        String type = scanner.nextLine();

        return new LiquidToxicCar(litres, ToxicCar.getToxicityLevel(type));
    }

    @Override
    public int getLitreCapacity() {
        return this.litres;
    }

    @Override
    public boolean didSpill() {
        Random rand = new Random();
        int i = rand.nextInt(100);
        return i == 1;
    }

    @Override
    public String toString() {
        return String.format("Liquid Toxins Car #%d. Litres: %d Toxicity: %s", this.id, this.litres, this.getToxicity());
    }

    public void stationAction() throws Exception {
        super.stationAction();

        if (this.didSpill()) {
            throw new ToxinSpilledException(this.id);
        }
    }
}

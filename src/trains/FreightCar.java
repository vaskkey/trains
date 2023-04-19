package trains;

import java.util.Random;

public class FreightCar extends TrainCar {
    public double cargoWeight = 0;

    public FreightCar() {
        this(32);
    }

    public FreightCar(int maxWeight) {
        this(false, maxWeight);
    }

    public FreightCar(boolean isElectric, int maxWeight) {
        super(isElectric, maxWeight);
    }

    @Override
    public String toString() {
        return String.format("Freight Car #%d. Max cargo weight: %f. Current weight: %f", this.id, this.maxGrossWeight, this.cargoWeight);
    }

    @Override
    public void stationAction() throws Exception {
        Random rand = new Random();
        this.cargoWeight = rand.nextDouble(this.maxGrossWeight);
    }

    @Override
    public void endRouteAction() {
        this.cargoWeight = 0;
    }
}

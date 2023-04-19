package trains;

import java.util.Random;

public class HeavyFreightCar extends TrainCar {
    public double cargoWeight = 0;

    public HeavyFreightCar() {
        this(32);
    }

    public HeavyFreightCar(int weight) {
        this(false, weight);
    }

    public HeavyFreightCar(boolean isElectric, int weight) {
        super(isElectric, weight);
    }

    @Override
    public String toString() {
        return String.format("Heavy Freight Car #%d. Max cargo weight: %f. Current weight: %f", this.id, this.maxGrossWeight, this.cargoWeight);
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

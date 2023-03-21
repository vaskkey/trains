package trains;

abstract public class TrainCar {
    protected boolean isElectric;
    /**
     * Described in metric tons
     */
    protected double weight;

    public TrainCar(boolean isElectric, double weight) {
        this.isElectric = isElectric;
        this.weight = weight;
    }
}

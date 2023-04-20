package trains;

abstract public class TrainCar {
    static int ID = 0;
    public final int id;
    protected boolean isElectric;
    /**
     * Described in metric tons
     */
    protected double maxGrossWeight;

    public TrainCar(boolean isElectric, double maxGrossWeight) {
        this.isElectric = isElectric;
        this.maxGrossWeight = maxGrossWeight;
        this.id = ID++;
    }

    public double getMaxGrossWeight() {
        return this.maxGrossWeight;
    }

    public abstract void stationAction() throws Exception;

    public abstract void endRouteAction();
}

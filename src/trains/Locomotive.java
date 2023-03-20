package trains;

import trains.exceptions.CarTooHeavyException;
import trains.exceptions.TooManyCarsException;
import trains.exceptions.TooManyElectricCarsException;

import java.util.ArrayList;

public class Locomotive {
    private static int lastId = 1;
    private int id;
    private final String name;
    private String parentStation;
    private final String startStation;
    private final String endStation;

    private final int maxCars;
    private final int maxElectricCars;
    private final double maxWeight;

    private final ArrayList<TrainCar> cars;

    public Locomotive(String name, String parentStation, String startStation, String endStation, int maxCars, int maxElectricCars, int maxWeight){
        this.id = lastId++;
       this.name = name;
       this.parentStation = parentStation;
       this.startStation = startStation;
       this.endStation = endStation;
       this.maxCars = maxCars;
       this.maxElectricCars = maxElectricCars;
       this.maxWeight = maxWeight;

       this.cars = new ArrayList<>();
    }

    public void addCar(TrainCar car) throws TooManyCarsException, TooManyElectricCarsException, CarTooHeavyException {
        if (cars.size() + 1 > this.maxCars) {
            throw new TooManyCarsException();
        }

        if (this.getCarsWeight() + car.weight > this.maxWeight) {
            throw new CarTooHeavyException();
        }

        if (car.isElectric) {
            if (this.getElectricCarsCount() + 1 >= this.maxElectricCars) {
                throw new TooManyElectricCarsException();
            }
        }

        this.cars.add(car);
    }

    @Override
    public String toString() {
       return String.format("A train %s#%d from %s, to %s", this.name, this.id, this.startStation, this.endStation);
    }

    private double getElectricCarsCount() {
        double i = 0;
        for (TrainCar car : this.cars) {
            if (car.isElectric) i++;
        }

        return i;
    }

    private double getCarsWeight() {
        double i = 0;
        for (TrainCar car : this.cars) {
           i += car.weight;
        }

        return i;
    }
}

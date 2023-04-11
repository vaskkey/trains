package trains;

import route.Route;
import route.Station;
import trains.exceptions.CarTooHeavyException;
import trains.exceptions.TooManyCarsException;
import trains.exceptions.TooManyElectricCarsException;

import java.util.ArrayList;

public class Locomotive {
	private static int lastId = 1;
	private final String name;
	private final Station startStation;
	private final Station endStation;
	private final Route route;
	private final int maxCars;
	private final int maxElectricCars;
	private final double maxWeight;
	private final ArrayList<TrainCar> cars;
	private final int id;
	private final String parentStation;

	public Locomotive(String name, Route route, String parentStation, int maxCars, int maxElectricCars, int maxWeight) {
		this.id = lastId++;
		this.name = name;
		this.parentStation = parentStation;
		this.startStation = route.getFrom();
		this.endStation = route.getTo();
		this.route = route;
		this.maxCars = maxCars;
		this.maxElectricCars = maxElectricCars;
		this.maxWeight = maxWeight;

		this.cars = new ArrayList<>();
	}

	public Locomotive addCar(TrainCar car) throws TooManyCarsException, TooManyElectricCarsException, CarTooHeavyException {
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

		return this;
	}

	@Override
	public String toString() {
		return String.format("A train %s#%d originating from %s, going from %s, to %s", this.name, this.id, this.parentStation, this.startStation.getName(), this.endStation.getName());
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

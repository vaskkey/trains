package trains;

import route.Route;
import route.Station;
import route.StationConnection;
import route.exceptions.LastStationException;
import trains.exceptions.CarTooHeavyException;
import trains.exceptions.RailRoadHazardException;
import trains.exceptions.TooManyCarsException;
import trains.exceptions.TooManyElectricCarsException;

import java.util.ArrayList;

public class Locomotive extends Thread {
	private final static String monitor = "";
	private static int lastId = 1;
	private final String name;
	private final Station startStation;
	private double speed = 150;
	private final Station endStation;
	private Route route;
	private Route returnRoute;
	private StationConnection currentConnection;
	private final int maxCars;
	private final int maxElectricCars;
	private final double maxWeight;
	private final ArrayList<TrainCar> cars;
	private final int id;
	private final String parentStation;

	public Locomotive(String name, Route route, Route returnRoute, String parentStation, int maxCars, int maxElectricCars, int maxWeight) {
		this.id = lastId++;
		this.name = name;
		this.parentStation = parentStation;
		this.startStation = route.getFrom();
		this.endStation = route.getTo();
		this.route = route;
		this.returnRoute = returnRoute;
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
			if (this.getElectricCarsCount() + 1 > this.maxElectricCars) {
				throw new TooManyElectricCarsException();
			}
		}

		this.cars.add(car);

		return this;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.move();
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public void move() throws InterruptedException {

		try {
			StationConnection connection;
			synchronized (monitor) {
				connection = this.route.moveToNextConnection(this.currentConnection);
			}

			if (connection == null) {
				Thread.sleep(500);
			} else {
				this.currentConnection = connection;
				System.out.printf("Train %s#%d leaves %s", this.name, this.id, connection.getFrom());
				System.out.println();
				Thread.sleep(this.currentConnection.calculateDuration(this.speed));
				System.out.printf("Train %s#%d arrives to %s with speed %5.2f", this.name, this.id, connection.getTo(), this.speed);
				System.out.println();

				synchronized (monitor) {
					this.route.removeBusyConnection(this.currentConnection);
				}
				Thread.sleep(2000);
				this.changeSpeed();
			}
		} catch (LastStationException e) {
			System.out.printf("Train %s arrived to the last station", this.name);
			System.out.println();
			Route tmp = this.route;

			this.route = returnRoute;
			this.returnRoute = tmp;
			Thread.sleep(30_000);
		} catch (RailRoadHazardException e) {
			System.out.printf("Train %s#%d is going too fast!", this.name, this.id);
			System.out.println();
		}
	}

	@Override
	public String toString() {
		return String.format("A train %s#%d originating from %s, going from %s, to %s", this.name, this.id, this.parentStation, this.startStation.getName(), this.endStation.getName());
	}

	private void changeSpeed() throws RailRoadHazardException {
		if (Math.random() > .5) {
			this.speed += this.speed * .03;
		} else {
			this.speed -= this.speed * .03;
		}

		if (this.speed >= 200) {
			throw new RailRoadHazardException();
		}
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

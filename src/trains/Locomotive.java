package trains;

import route.Route;
import route.Station;
import route.StationConnection;
import route.exceptions.LastStationException;
import trains.exceptions.*;

import java.util.ArrayList;
import java.util.Optional;

public class Locomotive extends Thread {
    private static final int DEFAULT_MAX_WEIGHT = 25;
    private final static String monitor = "";
    private static int lastId = 1;
    private final String name;
    private final Station startStation;
    private final Station endStation;
    private final int maxCars;
    private final int maxElectricCars;
    private final double maxWeight;
    private final ArrayList<TrainCar> cars;
    private final int trainId;
    private final String parentStation;
    private InterruptCb cb;
    private double speed = 1.3;
    private Route route;
    private Route returnRoute;
    private StationConnection currentConnection;

    public Locomotive(String name, Route route, Route returnRoute, String parentStation, int maxCars, int maxElectricCars, int maxWeight) {
        if (maxElectricCars > maxCars) maxElectricCars = maxCars;
        if (maxCars < 0) maxCars = 0;
        if (maxWeight < 0) maxWeight = DEFAULT_MAX_WEIGHT;

        this.trainId = lastId++;
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

    public void addCar(TrainCar car) throws TooManyCarsException, TooManyElectricCarsException, CarTooHeavyException {
        if (cars.size() + 1 > this.maxCars) {
            throw new TooManyCarsException();
        }

        if (this.getCarsWeight() + car.maxGrossWeight > this.maxWeight) {
            throw new CarTooHeavyException();
        }

        if (car.isElectric) {
            if (this.getElectricCarsCount() + 1 > this.maxElectricCars) {
                throw new TooManyElectricCarsException();
            }
        }

        this.cars.add(car);
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.move();
            } catch (InterruptedException e) {
                System.out.printf("Train %s has been removed%n", this);
                synchronized (monitor) {
                    this.cb.onInterupt(this);
                    this.route.removeBusyConnection(this.currentConnection);
                }
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
                Thread.sleep(this.currentConnection.calculateDuration(this.speed));

                this.performCarActions();

                synchronized (monitor) {
                    this.route.removeBusyConnection(this.currentConnection);
                }
                Thread.sleep(2000);
                this.changeSpeed();
            }
        } catch (LastStationException e) {
            Route tmp = this.route;

            this.route = returnRoute;
            this.returnRoute = tmp;
            this.performLastStationActions();
            Thread.sleep(30_000);
        } catch (RailRoadHazardException e) {
            System.out.printf("Train %s#%d is going too fast!%n", this.name, this.trainId);
        }
    }

    public synchronized void printInfo() {
        System.out.println(this);
    }

    public synchronized void printStatus() {
        System.out.printf(this.getStatus());
    }

    public synchronized String getStatus() {
        int p = getPercentDone();
        String str = p > -1 ? String.format("%d percent done with its route.%n Currently moving between %s and %s%n", p, this.currentConnection.getFrom(), this.currentConnection.getTo())
                : "Waiting before moving on\n";
        return String.format("A train %s originating from %s, going from %s, to %s. %s", this, this.parentStation, this.startStation.getName(), this.endStation.getName(), str);
    }

    public void performCarActions() throws InterruptedException {
        for (TrainCar car : this.cars) {
            try {
                car.stationAction();
            } catch (CarSpilledException e) {
                Thread.sleep(500);
                break;
            } catch (ToxinSpilledException e) {
                System.out.println(e.getMessage());
                Thread.sleep(1500);
                break;
            } catch (CarBlewUpException e) {
                System.out.println(e.getMessage());
                this.interrupt();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    public void performLastStationActions() {
        this.cars.forEach(TrainCar::endRouteAction);
    }


    public void printCars() {
        this.cars.forEach(System.out::println);
    }

    public void removeCar(int id) throws NotFound {
        Optional<TrainCar> car = this.cars.stream().filter(c -> c.id == id).findFirst();

        if (car.isEmpty()) {
            throw new NotFound("Car");
        }

        this.cars.remove(car.get());
        this.printCars();
    }

    public int getTrainId() {
        return this.trainId;
    }

    synchronized public int getPercentDone() {
        return this.route.getPercentDone(this.currentConnection);
    }

    private void changeSpeed() throws RailRoadHazardException {
        if (Math.random() > .5) {
            this.speed += this.speed * .03;
        } else {
            this.speed -= this.speed * .03;
        }

        if (this.speed >= 2) {
            this.speed = 1;
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
            i += car.maxGrossWeight;
        }

        return i;
    }

    @Override
    public String toString() {
        return String.format("%s#%d", this.name, this.trainId);
    }

    public void setOnInterrupt(InterruptCb cb) {
        this.cb = cb;
    }
}

package cmd;

import route.RouteBuilder;
import route.Station;
import route.StationsMatrix;
import trains.Locomotive;
import trains.TrainCar;
import trains.TrainFactory;
import trains.exceptions.NotFound;
import trains.exceptions.TooManyCarsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {
    StationsMatrix stations;
    ArrayList<Locomotive> trains;

    public Cli() {
        this.init();
    }

    private void init() {
        stations = RouteBuilder.loadStations();
        trains = TrainFactory.loadTrains(stations);

        trains.forEach(t -> t.setOnInterrupt(this.trains::remove));
        trains.forEach(Thread::start);

        Logger logger = new Logger(this::getAllTrainsStatus);
        logger.start();

        while (true) {
            System.out.println("Available commands: exit, print_stations, print_station add_station, print_all_trains_status, print_all_trains, print_train, add_train, remove_train, add_trains, print_cars add_cars, remove_car");
            Scanner scanner = new Scanner(System.in);
            try {
                this.parseCommand(scanner.nextLine(), scanner);
            } catch (ExitException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }

    private void parseCommand(String str, Scanner scanner) throws ExitException {
        switch (str) {
            case "exit" -> throw new ExitException();
            case "print_stations" -> this.printStations();
            case "print_station" -> this.printStation(scanner);
            case "add_station" -> this.addStation(scanner);
            case "print_train" -> this.printTrain(scanner);
            case "add_train" -> this.addTrain(scanner);
            case "remove_train" -> this.removeTrain(scanner);
            case "print_all_trains" -> this.printAllTrains();
            case "print_all_trains_status" -> this.printAllTrainsStatus();
            case "print_cars" -> this.printCars(scanner);
            case "add_cars" -> this.addCars(scanner);
            case "remove_car" -> this.removeCar(scanner);
        }
    }

    private void printStations() {
        this.stations.print();
    }

    private void printStation(Scanner scanner) {
        System.out.println("Station Name:");
        String params = scanner.nextLine();

        try {
            Station st = this.stations.findByName(params);

            System.out.println(st);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addStation(Scanner scanner) {
        Station station;
        System.out.println("Provide params separated by comma `name,x,y`");
        String params = scanner.nextLine();

        if (params.equals("abort")) return;

        try {
            station = RouteBuilder.parseParams(params, this.stations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.addStation(scanner);
            return;
        }

        this.stations.addStation(station);
    }

    private void addTrain(Scanner scanner) {
        Locomotive loco;
        System.out.println("Provide params separated by comma `name,from,to,parent,maxcars,maxElectricCars,maxWeight`");
        String params = scanner.nextLine();

        if (params.equals("abort")) return;

        try {
            loco = TrainFactory.parseParams(this.stations, params);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.addTrain(scanner);
            return;
        }

        this.addCars(loco, scanner);

        this.trains.add(loco);
        System.out.printf("%s is on it's way%n", loco);
        loco.start();
    }

    private void addCars(Scanner scanner) {
        System.out.println("Train ID:");
        String params = scanner.nextLine();

        try {
            Locomotive loco = this.findTrain(params);
            this.addCars(loco, scanner);
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid ID");
        }
    }

    private void addCars(Locomotive locomotive, Scanner scanner) {
        System.out.println("what type of a car would you like to add?");
        System.out.println("available types: passenger, mail, baggage, restaurant, freight, heavy_freight, liquid, cooling, gas, explosives, toxic liq_toxic");
        String params = scanner.nextLine();

        while (!params.equals("done")) {
            try {
                TrainCar car = TrainFactory.getCar(params, scanner);
                locomotive.addCar(car);
            } catch (TooManyCarsException err) {
                System.out.println(err.getMessage());
                break;
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }

            System.out.println("what type of a car would you like to add?");
            System.out.println("available types: passenger, mail, baggage, restaurant, freight, heavy_freight, liquid, cooling, gas, explosives, toxic liq_toxic");
            params = scanner.nextLine();
        }
    }

    private void printAllTrains() {
        this.trains.forEach(Locomotive::printInfo);
    }

    private void printAllTrainsStatus() {
        this.trains.stream().sorted((a, b) -> b.getPercentDone() - a.getPercentDone()).forEach(Locomotive::printStatus);
    }

    private List<String> getAllTrainsStatus() {
        return this.trains.stream().sorted((a, b) -> b.getPercentDone() - a.getPercentDone()).map(locomotive -> {
            return locomotive.getStatus() + '\n' + locomotive.getCars();
        }).toList();
    }

    private void printTrain(Scanner scanner) {
        System.out.println("Train ID:");
        String params = scanner.nextLine();

        try {
            Locomotive loco = this.findTrain(params);

            loco.printStatus();
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid ID");
        }
    }

    private void printCars(Scanner scanner) {
        System.out.println("Train ID:");
        String params = scanner.nextLine();

        try {
            Locomotive loco = this.findTrain(params);

            loco.printCars();
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid ID");
        }
    }

    private void removeTrain(Scanner scanner) {
        System.out.println("Train ID:");
        String params = scanner.nextLine();

        try {
            Locomotive loco = this.findTrain(params);
            loco.interrupt();
        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid ID");
        }
    }

    private void removeCar(Scanner scanner) {
        System.out.println("Train ID:");
        String params = scanner.nextLine();

        try {
            Locomotive loco = this.findTrain(params);
            loco.printCars();
            System.out.println("ID of a car to remove:");
            String carParam = scanner.nextLine();
            int carID = Integer.parseInt(carParam);

            loco.removeCar(carID);

        } catch (NotFound e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid ID");
        }
    }

    private Locomotive findTrain(String idStr) throws Exception {
        int id = Integer.parseInt(idStr);
        Optional<Locomotive> loco = this.trains.stream().filter(l -> l.getTrainId() == id).findFirst();

        if (loco.isEmpty()) {
            throw new NotFound("Train");
        }

        return loco.get();
    }
}

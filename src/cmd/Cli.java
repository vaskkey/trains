package cmd;

import route.RouteBuilder;
import route.StationsMatrix;
import trains.Locomotive;
import trains.TrainCar;
import trains.TrainFactory;
import trains.exceptions.NotFound;
import trains.exceptions.TooManyCarsException;

import java.util.*;

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
            System.out.println("Available commands: exit, add_train, print_stations, print_all_trains, print_train, print_all_trains_status, remove_train");
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
            case "add_train" -> this.addTrain(scanner);
            case "print_stations" -> this.printStations();
            case "print_train" -> this.printTrain(scanner);
            case "print_all_trains" -> this.printAllTrains();
            case "print_all_trains_status" -> this.printAllTrainsStatus();
            case "print_cars" -> this.printCars(scanner);
            case "remove_train" -> this.removeTrain(scanner);
            case "add_cars" -> this.addCars(scanner);
            case "remove_car" -> this.removeCar(scanner);
        }
    }

    private void printStations() {
        this.stations.print();
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
        this.trains.stream().sorted(Comparator.comparingInt(Locomotive::getPercentDone)).forEach(Locomotive::printStatus);
    }

    private List<String> getAllTrainsStatus() {
        return this.trains.stream().sorted(Comparator.comparingInt(Locomotive::getPercentDone)).map(Locomotive::getStatus).toList();
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

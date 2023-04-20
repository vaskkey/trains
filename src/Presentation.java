import cmd.Logger;
import route.Route;
import route.RouteBuilder;
import route.StationsMatrix;
import route.exceptions.StationNotFound;
import trains.Locomotive;
import trains.TrainCar;
import trains.TrainFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class Presentation {
    static StationsMatrix stations;
    static ArrayList<Locomotive> trains;

    public static void main(String[] args) {
        presentReadingFromFiles();
        presentAddingTrain();

        presentWritingLogsAndTrainMultithreading();
    }

    public static void presentReadingFromFiles() {
        stations = RouteBuilder.loadStations();
        trains = TrainFactory.loadTrains(stations);

        System.out.println("Reading stations from file:");
        stations.print();

        System.out.println("\nPrint cars for each train:");
        trains.forEach(t -> {
            System.out.println(t);
            t.printCars();
        });
    }

    public static void presentAddingTrain() {
        System.out.println("\nCreate a new train an add cars to it");
        String[] types = {"passenger", "mail", "baggage", "restaurant", "freight", "heavy_freight", "liquid", "cooling", "gas", "explosives", "toxic", "liq_toxic"};

        try {
            Route route = stations.getPath("Oakland", "Evansville");
            Locomotive loco = new Locomotive("Bigby", route, route.getReturnRoute(), "Rochester", 25, 12, 500);

            for (String type : types) {
                TrainCar car = TrainFactory.getCar(type);
                loco.addCar(car);
            }

            System.out.println(loco);
            loco.printCars();
            trains.add(loco);
        } catch (StationNotFound e) {
            System.out.println("Station not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void presentWritingLogsAndTrainMultithreading() {
        System.out.println("\nLaunch all trains, write logs to `AppData.txt`, and watch last train do its thing in console:");

        //print only last train to console for readability
        Locomotive lastTrain = trains.get(trains.size() - 1);

        trains.forEach(t -> t.setOnInterrupt(trains::remove));
        trains.forEach(Thread::start);

        Logger logger = new Logger(() -> {
            lastTrain.printStatus();
            System.out.println();
            lastTrain.printCars();
            System.out.println();
            return trains.stream().sorted(Comparator.comparingInt(Locomotive::getPercentDone)).map(Locomotive::getStatus).toList();
        });


        logger.start();
        try {
            logger.join();
        } catch (InterruptedException e) {
            System.out.println("Logger dead");
        }
        for (Locomotive t : trains) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.printf("%s removed%n", t);
            }
        }
    }
}

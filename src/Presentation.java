import route.Route;
import route.RouteBuilder;
import route.StationsMatrix;
import route.exceptions.StationNotFound;
import trains.BaggageCar;
import trains.Locomotive;

public class Presentation {
    public static void main(String[] args) {
        StationsMatrix stations = RouteBuilder.loadStations();

        Route route;
        Route route2;
        try {
            route = stations.getPath("McAllen", "Marysville");
            route2 = stations.getPath("Orlando", "San Antonio");
        } catch (StationNotFound e) {
            System.out.println(e.getMessage());
            return;
        }

        Route returnRoute = route.getReturnRoute();
        Route returnRoute2 = route2.getReturnRoute();

        Locomotive loco = new Locomotive("Hania Rani", route, returnRoute, "Podlasie.", 3, 2, 1000);
        Locomotive loco2 = new Locomotive("Chopin", route, returnRoute, "Podlasie.", 3, 2, 1000);
        Locomotive loco3 = new Locomotive("Mozart", route2, returnRoute2, "Podlasie.", 3, 2, 1000);
        Locomotive loco4 = new Locomotive("Bach", route2, returnRoute2, "Podlasie.", 3, 2, 1000);

        try {
            loco.addCar(new BaggageCar());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        loco.start();
        loco2.start();
        loco3.start();
        loco4.start();

        try {
            loco.join();
            loco2.join();
            loco3.join();
            loco4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

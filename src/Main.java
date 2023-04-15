import route.Route;
import route.RouteBuilder;
import trains.BaggageCar;
import trains.Locomotive;

public class Main {
	public static void main(String[] args) {
		String stations = "Warszawa Zachodnia,Warszawa Centralna,Warszawa Wschodnia,Shire,Rohan,Mordor";
		Route route = RouteBuilder.getRoute(stations);
		Route reverseRoute = RouteBuilder.getReverseRoute(stations);

		Locomotive loco = new Locomotive("Hania Rani", route, reverseRoute, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco2 = new Locomotive("Chopin", route, reverseRoute, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco3 = new Locomotive("Mozart", route, reverseRoute, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco4 = new Locomotive("Bach", route, reverseRoute, "Podlasie cnie.", 3, 2, 1000);

		try {
			loco.addCar(new BaggageCar());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		loco.start();
		loco2.start();
		loco3.start();
		loco4.start();
	}
}

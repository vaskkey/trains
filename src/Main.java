import route.Route;
import route.Station;
import route.StationConnection;
import trains.BaggageCar;
import trains.Locomotive;

public class Main {
	public static void main(String[] args) {
		Station st1 = new Station("Warszawa Zachodnia");
		Station st2 = new Station("Warszawa Centralna");
		Station st3 = new Station("Warszawa Wschodnia");

		StationConnection sc1 = new StationConnection(st1, st2);
		StationConnection sc2 = new StationConnection(st2, st3);

		Route route = new Route();
		route.addConection(sc1).addConection(sc2);

		Locomotive loco = new Locomotive("Bajlando", route, "Podlasie cnie.", 3, 2, 1000);

		try {
			loco.addCar(new BaggageCar());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		System.out.println(loco);
		loco.move();
		loco.move();
		loco.move();
	}
}

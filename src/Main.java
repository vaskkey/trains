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
		Station st4 = new Station("Mordor");
		Station st5 = new Station("Shire");
		Station st6 = new Station("Rohan");

		StationConnection sc1 = new StationConnection(st1, st2);
		StationConnection sc2 = new StationConnection(st2, st3);
		StationConnection sc3 = new StationConnection(st3, st4);
		StationConnection sc4 = new StationConnection(st4, st5);
		StationConnection sc5 = new StationConnection(st5, st6);

		Route route = new Route();
		route.addConection(sc1).addConection(sc2).addConection(sc3).addConection(sc4).addConection(sc5);

		Locomotive loco = new Locomotive("Hania Rani", route, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco2 = new Locomotive("Chopin", route, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco3 = new Locomotive("Mozart", route, "Podlasie cnie.", 3, 2, 1000);
		Locomotive loco4 = new Locomotive("Bach", route, "Podlasie cnie.", 3, 2, 1000);

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

package route;

public class RouteBuilder {
	public static Route getRoute(String str) {
		String[] stations = str.split(",");
		Route route = new Route();
		Station prev = new Station(stations[0]);


		for (int i = 1; i < stations.length; i++) {
			Station st = new Station(stations[i]);
			StationConnection sc = new StationConnection(prev, st);
			route.addConection(sc);

			prev = st;
		}

		return route;
	}

	public static Route getReverseRoute(String str) {
		String[] stations = str.split(",");
		Route route = new Route();
		Station prev = new Station(stations[stations.length - 1]);


		for (int i = stations.length - 2; i >= 0; i--) {
			Station st = new Station(stations[i]);
			StationConnection sc = new StationConnection(prev, st);
			route.addConection(sc);

			prev = st;
		}

		return route;
	}
}

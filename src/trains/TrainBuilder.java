package trains;

import route.Route;

public class TrainBuilder {
	public static Locomotive getTrain(Route route, Route reverseRoute, String params) throws Exception {
		// "name,origin,max_cars,max_electric,max_weight"
		String[] p = params.split(",");

		if (p.length != 5) {
			throw new Exception("Invalid params, try again");
		}

		String name = p[0];
		String parent = p[1];
		int maxCars, maxElectric, maxWeight;

		try {
			maxCars = Integer.parseInt(p[2]);
			maxElectric = Integer.parseInt(p[3]);
			maxWeight = Integer.parseInt(p[4]);
		} catch (Exception e) {
			throw new Exception("Invalid params, try again");
		}


		return new Locomotive(name, route, reverseRoute, parent, maxCars, maxElectric, maxWeight);
	}
}

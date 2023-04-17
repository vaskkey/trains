package cmd;

import route.Route;
import route.RouteBuilder;
import trains.TrainBuilder;

import java.util.Scanner;

public class Cli {
	public Cli() {
		this.init();
	}

	private void init() {
		while (true) {
			System.out.println("Available commands: add_route");
			Scanner scanner = new Scanner(System.in);
			try {
				this.parseCommand(scanner.nextLine());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
	}

	private void parseCommand(String str) throws Exception {
		switch (str) {
			case "exit" -> throw new Exception("Exit application");
			case "add_route" -> this.addRoute();
		}
	}

	private void addRoute() {
		System.out.println("Provide stations separated by comma e.g. `Shire,Rivendell,Moria,Lothlorien`");
		Scanner scanner = new Scanner(System.in);
		String stations = scanner.nextLine();

		Route route = RouteBuilder.getRoute(stations);
		Route returnRoute = RouteBuilder.getReverseRoute(stations);

		System.out.println("Provide train params to add to this route e.g. name,parent_station,max_cars,max_electric,max_weight");

		try {
			String params = scanner.nextLine();
			TrainBuilder.getTrain(route, returnRoute, params);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

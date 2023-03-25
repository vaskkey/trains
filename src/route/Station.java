package route;

public class Station {
	private final String name;
	private final Station nextStation;
	private final Station prevStation;

	public Station(String name, Station nextStation, Station prevStation) {
		this.name = name;
		this.nextStation = nextStation;
		this.prevStation = prevStation;
	}

	@Override
	public String toString() {
		return String.format("Station: %s. Between %s and %s", this.name, this.prevStation.name, this.nextStation.name);
	}
}

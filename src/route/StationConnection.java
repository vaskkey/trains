package route;

public class StationConnection {
	private static int ID_COUNT;
	private final int id;
	private final Station to;
	private final Station from;


	public StationConnection(Station from, Station to) {
		this.to = to;
		this.from = from;
		this.id = ID_COUNT++;
	}

	public Station getTo() {
		return this.to;
	}

	public Station getFrom() {
		return this.from;
	}
}

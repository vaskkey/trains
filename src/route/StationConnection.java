package route;

public class StationConnection {
	private static int ID_COUNT;
	private final int id;
	public final int lengthKm;
	private final Station to;
	private final Station from;


	public StationConnection(Station from, Station to) {
		this(from, to, 10);
	}

	public StationConnection(Station from, Station to, int lengthKm) {
		this.to = to;
		this.from = from;
		this.id = ID_COUNT++;
		this.lengthKm = lengthKm;
	}

	public Station getTo() {
		return this.to;
	}

	public Station getFrom() {
		return this.from;
	}
}

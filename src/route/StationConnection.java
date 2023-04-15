package route;

public class StationConnection {
	private static int ID_COUNT;
	private final int id;
	private final int length;
	private final Station to;
	private final Station from;


	public StationConnection(Station from, Station to) {
		this(from, to, 1000);
	}

	public StationConnection(Station from, Station to, int length) {
		this.to = to;
		this.from = from;
		this.id = ID_COUNT++;
		this.length = length;
	}

	public Station getTo() {
		return this.to;
	}

	public Station getFrom() {
		return this.from;
	}

	public long calculateDuration(double speed) {
		return Math.round(this.length / speed) * 1000;
	}
}

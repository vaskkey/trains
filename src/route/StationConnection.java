package route;

import java.util.ArrayList;
import java.util.Optional;

public class StationConnection {
    private static final ArrayList<StationConnection> ALL_CONNECTIONS = new ArrayList<>();
    private static int ID_COUNT;
    public final int length;
    private final int id;
    private final Station to;
    private final Station from;

    public StationConnection(Station from, Station to) {
        this.to = to;
        this.from = from;
        this.id = ID_COUNT++;
        int xSq = (int) Math.pow(from.getX() - to.getX(), 2);
        int ySq = (int) Math.pow(from.getY() - to.getY(), 2);
        this.length = (int) Math.sqrt(xSq + ySq);
    }

    public static StationConnection getConnection(Station from, Station to) {
        Optional<StationConnection> conn = ALL_CONNECTIONS.stream().filter(connection -> connection.getTo() == to && connection.getFrom() == from).findFirst();

        if (conn.isEmpty()) {
            StationConnection sc = new StationConnection(from, to);
            ALL_CONNECTIONS.add(sc);
            return sc;
        }

        return conn.get();
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

    @Override
    public String toString() {
        return String.format("Connection between %s and %s. With length of %d", this.from.getName(), this.to.getName(), this.length);
    }
}

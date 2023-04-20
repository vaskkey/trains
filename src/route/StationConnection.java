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

        this.length = calculateLenth(from.getX(), to.getX(), getFrom().getY(), to.getY());
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

    public static int calculateLenth(int x1, int x2, int y1, int y2) {
        double xSq = Math.pow(x1 - x2, 2);
        double ySq = Math.pow(y1 - y2, 2);

        return (int) Math.sqrt(xSq + ySq);
    }
}

package route;

import route.exceptions.LastStationException;

import java.util.ArrayList;

public class Route {
    private static ArrayList<StationConnection> busyConnections;
    ArrayList<StationConnection> connections;

    public Route(ArrayList<StationConnection> connections) {
        this.connections = connections;
        busyConnections = new ArrayList<>();
    }

    public Route() {
        this(new ArrayList<>());
    }

    public void addConection(StationConnection connection) {
        this.connections.add(connection);
    }

    public Route getReturnRoute() {
        Route r = new Route();

        for (int i = this.connections.size() - 1; i >= 0; i--) {
            StationConnection conn = StationConnection.getConnection(this.connections.get(i).getTo(), this.connections.get(i).getFrom());
            r.addConection(conn);
        }

        return r;
    }

    public StationConnection moveToNextConnection(StationConnection connection) throws LastStationException {
        if (connection == null) {
            StationConnection conn = this.connections.get(0);
            boolean isBusy = this.connectionBusy(conn);
            return isBusy ? null : this.setBusyConnection(conn);
        }

        StationConnection nextConnection;

        try {
            nextConnection = this.getNextConnection(connection);
        } catch (IndexOutOfBoundsException err) {
            throw new LastStationException();
        }

        boolean isBusy = this.connectionBusy(nextConnection);
        return isBusy ? null : this.setBusyConnection(nextConnection);
    }

    public void removeBusyConnection(StationConnection connection) {
        busyConnections.remove(connection);
    }

    private StationConnection getNextConnection(StationConnection connection) {
        int idx = this.connections.indexOf(connection);

        return this.connections.get(idx + 1);
    }

    private StationConnection setBusyConnection(StationConnection connection) {
        busyConnections.add(connection);
        return connection;
    }


    private boolean connectionBusy(StationConnection connection) {
        return busyConnections.contains(connection);
    }

    public Station getFrom() {
        return this.connections.get(0).getFrom();
    }

    public Station getTo() {
        return this.connections.get(this.connections.size() - 1).getTo();
    }

    public int getPercentDone(StationConnection connection) {
        int i = this.connections.indexOf(connection);

        if (i == -1) {
            return i;
        }

        int length = 0;
        for (int j = 0; j <= i; j++) {
            length += this.connections.get(j).length;
        }

        return (length * 100) / this.getLength();
    }

    public int getLength() {
        int length = 0;
        for (StationConnection sc : this.connections) {
            length += sc.length;
        }

        return length;
    }

    @Override
    public String toString() {
        return String.format("A route from %s to %s", this.getFrom().getName(), this.getTo().getName());
    }
}

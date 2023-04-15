package route;

import route.exceptions.LastStationException;

import java.util.ArrayList;

public class Route {
	ArrayList<StationConnection> connections;
	ArrayList<StationConnection> busyConnections;

	public Route(ArrayList<StationConnection> connections) {
		this.connections = connections;
		this.busyConnections = new ArrayList<>();
	}

	public Route() {
		this(new ArrayList<>());
	}

	public Route addConection(StationConnection connection) {
		this.connections.add(connection);
		return this;
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
		this.busyConnections.remove(connection);
	}

	private StationConnection getNextConnection(StationConnection connection) {
		int idx = this.connections.indexOf(connection);

		return this.connections.get(idx + 1);
	}

	private StationConnection setBusyConnection(StationConnection connection) {
		this.busyConnections.add(connection);
		return connection;
	}


	private boolean connectionBusy(StationConnection connection) {
		return this.busyConnections.contains(connection);
	}

	public Station getTo() {
		return this.connections.get(0).getFrom();
	}

	public Station getFrom() {
		return this.connections.get(this.connections.size() - 1).getTo();
	}

	@Override
	public String toString() {
		return String.format("A route from %s to %s", this.getFrom().getName(), this.getTo().getName());
	}
}

package route;

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

	public void setBusyConnection(StationConnection connection) {
		this.busyConnections.add(connection);
	}

	public void removeBusyConnection(StationConnection connection) {
		this.busyConnections.remove(connection);
	}

	public boolean connectionBusy(StationConnection connection) {
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

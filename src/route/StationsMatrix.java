package route;

import route.exceptions.StationNotFound;

import java.util.Arrays;
import java.util.Comparator;

public class StationsMatrix {
    private final int xLength;
    private int lastY = 0;
    private int lastX = 0;
    private Station[][] stations;

    public StationsMatrix(int yLength, int xLength) {
        this.stations = new Station[yLength][xLength];
        this.xLength = xLength;
    }

    public void addStation(Station st) {
        if (this.lastX >= this.xLength) {
            this.lastY++;
            this.lastX = 0;
        }

        this.stations[this.lastY][this.lastX++] = st;
    }

    public void sort() {
        Station[] st = this.flatten();
        Arrays.sort(st, Comparator.comparingInt(Station::getY));
        this.stations = new Station[this.stations.length][xLength];

        int k = 0;
        for (int i = 0; i < st.length; i += this.xLength) {
            System.arraycopy(st, i, this.stations[k], 0, this.xLength);
            Arrays.sort(this.stations[k], Comparator.comparingInt(Station::getX));
            k++;
        }
    }

    public Coordinates getStationMatrixXYByName(String name) throws StationNotFound {
        for (int i = 0; i < this.stations.length; i++) {
            for (int j = 0; j < this.stations[i].length; j++) {
                if (this.stations[i][j].getName().equals(name)) {
                    return new Coordinates(j, i);
                }
            }
        }

        throw new StationNotFound();
    }

    public Route getPath(String from, String to) throws StationNotFound {
        Coordinates fromXY = this.getStationMatrixXYByName(from);
        Coordinates toXY = this.getStationMatrixXYByName(to);

        return this.getPath(fromXY, toXY);
    }

    public Route getPath(Coordinates from, Coordinates to) {
        Station tmp = this.stations[from.y][from.x];
        Route route = new Route();
        if (from.x < to.x) {
            for (int i = from.x + 1; i <= to.x; i++) {
                Station st = this.stations[from.y][i];
                StationConnection sc = StationConnection.getConnection(tmp, st);
                route.addConection(sc);
                tmp = st;
            }
        }

        if (from.x > to.x) {
            for (int i = from.x - 1; i >= to.x; i--) {
                Station st = this.stations[from.y][i];
                StationConnection sc = StationConnection.getConnection(tmp, st);
                route.addConection(sc);
                tmp = st;
            }
        }

        if (from.y < to.y) {
            for (int i = from.y + 1; i <= to.y; i++) {
                Station st = this.stations[i][to.x];
                StationConnection sc = StationConnection.getConnection(tmp, st);
                route.addConection(sc);
                tmp = st;
            }
        }

        if (from.y > to.y) {
            for (int i = from.y - 1; i >= to.y; i--) {
                Station st = this.stations[i][to.x];
                StationConnection sc = StationConnection.getConnection(tmp, st);
                route.addConection(sc);
                tmp = st;
            }
        }

        return route;
    }

    private Station[] flatten() {
        Station[] st = new Station[this.stations.length * this.xLength];
        int k = 0;
        for (Station[] station : this.stations) {
            for (Station value : station) {
                st[k++] = value;
            }
        }

        return st;
    }

    public void print() {
        for (Station[] stt : this.stations) {
            System.out.println(Arrays.toString(stt));
        }
    }
}

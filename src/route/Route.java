package route;

import java.util.ArrayList;

public class Route {
    ArrayList<Station> stations;

    public Route(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public Route() {
        this(new ArrayList<>());
    }

    public void addStation(Station station) {
        this.stations.add(station);
    }
}

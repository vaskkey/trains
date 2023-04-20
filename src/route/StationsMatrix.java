package route;

import route.exceptions.StationNotFound;

import java.util.*;

public class StationsMatrix {
    static int MAX_DISTANCE = 30;
    HashMap<Station, ArrayList<Station>> graph;
    ArrayList<Station> stations;

    public StationsMatrix(ArrayList<Station> stations) {
        this.graph = new HashMap<>();
        this.stations = stations;

        this.buildGraph(graph, stations);
    }

    public void buildGraph(HashMap<Station, ArrayList<Station>> graph, ArrayList<Station> stations) {
        for (int i = 0; i < stations.size(); i++) {
            Station st = stations.get(i);
            ArrayList<Station> neighbours = new ArrayList<>();

            for (int j = 0; j < stations.size(); j++) {
                Station st2 = stations.get(j);
                if (i == j) continue;

                int k = StationConnection.calculateLenth(st.getX(), st2.getX(), st.getY(), st2.getY());

                if (k < MAX_DISTANCE) {
                    neighbours.add(st2);
                }
            }

            graph.put(st, neighbours);
        }
    }

    public void addStation(Station st) {
        this.stations.add(st);
    }

    // Implemented DFS algorithm after several experiments with other approaches
    public Route getPath(String from, String to) throws StationNotFound {
        Optional<Station> fromSt = this.stations.stream().filter(s -> s.getName().equals(from)).findFirst();
        Optional<Station> toSt = this.stations.stream().filter(s -> s.getName().equals(to)).findFirst();

        if (fromSt.isEmpty()) throw new StationNotFound();
        if (toSt.isEmpty()) throw new StationNotFound();

        Map<Station, Station> parent = new HashMap<>();
        Stack<Station> currentStack = new Stack<>();
        Set<Station> visited = new HashSet<>();

        currentStack.push(fromSt.get());
        visited.add(fromSt.get());
        parent.put(fromSt.get(), null);

        Route route = new Route();

        while (!currentStack.isEmpty()) {
            Station currStation = currentStack.pop();

            if (currStation == toSt.get()) {
                ArrayList<StationConnection> tmpList = new ArrayList<>();

                while (currStation != null) {
                    Station tmpStation = parent.get(currStation);
                    if (tmpStation != null) {
                        tmpList.add(StationConnection.getConnection(tmpStation, currStation));
                    }

                    currStation = tmpStation;
                }

                Collections.reverse(tmpList);
                tmpList.forEach(route::addConection);
                tmpList.forEach(System.out::println);

                return route;
            }

            ArrayList<Station> neighbours = this.graph.get(currStation);

            for (Station neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    currentStack.add(neighbour);
                    parent.put(neighbour, currStation);
                }
            }
        }

        return route;
    }


    public void print() {
        this.stations.forEach(System.out::println);
    }
}

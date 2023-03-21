package route;

public class Station {
    private final String name;

    public Station(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Station: %s", this.name);
    }
}

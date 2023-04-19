package route;

public class Station {
    private final String name;
    private final int x;
    private final int y;

    public Station(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Station: %s. Coordinates: x:%d y:%d", this.name, this.x, this.y);
    }
}

package trains.exceptions;

public class NotFound extends Exception {
    public NotFound(String val) {
        super(String.format("%s not found", val));
    }

    public NotFound() {
        this("Item");
    }
}

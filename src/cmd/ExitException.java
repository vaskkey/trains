package cmd;

public class ExitException extends Exception {
    public ExitException() {
        super("Exit Application");
    }
}

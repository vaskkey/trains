package trains;

import java.util.Random;
import java.util.Scanner;

public class MailCar extends TrainCar implements IMailCar {
    private final Size mailSize;
    private int numPackages = 0;

    public MailCar(Size size) {
        super(true, 25);
        this.mailSize = size;
    }

    public MailCar() {
        this(Size.SMALL);
    }

    public static MailCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What size packages are allowed? BIG, MEDIUM, SMALL(default)");
        String size = scanner.nextLine();

        return new MailCar(BaggageCar.getSizeWithDefault(size));
    }

    @Override
    public Size getMailSize() {
        return this.mailSize;
    }

    @Override
    public String getMailSizeDescription() {
        return switch (this.mailSize) {
            case SMALL -> "Small size mail";
            case MEDIUM -> "Medium size mail";
            case BIG -> "Big size mail";
        };
    }

    private void setPackages() {
        Random rand = new Random();
        this.numPackages = switch (this.mailSize) {
            case SMALL -> rand.nextInt(250);
            case MEDIUM -> rand.nextInt(125);
            case BIG -> rand.nextInt(75);
        };
    }

    @Override
    public String toString() {
        return String.format("Mail Car #%d. %s. Number of packages: %d", this.id, this.getMailSizeDescription(), this.numPackages);
    }

    @Override
    public void stationAction() {
        this.setPackages();
    }

    @Override
    public void endRouteAction() {
        this.numPackages = 0;
    }
}

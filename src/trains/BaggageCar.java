package trains;

import java.util.Random;
import java.util.Scanner;

public class BaggageCar extends TrainCar implements IMailCar, IBaggageCar {
    private final Size mailSize;
    private final Size baggageSize;
    private int numPackages = 0;
    private int numBaggage = 0;

    public BaggageCar(Size baggageSize, Size mailSize) {
        super(false, 26);
        this.mailSize = mailSize;
        this.baggageSize = baggageSize;
    }

    public BaggageCar() {
        this(Size.SMALL, Size.SMALL);
    }

    public static BaggageCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What size packages are allowed? BIG, MEDIUM, SMALL(default)");
        String pSize = scanner.nextLine();
        System.out.println("What size baggage is allowed? BIG, MEDIUM, SMALL(default)");
        String bSize = scanner.nextLine();

        return new BaggageCar(getSizeWithDefault(bSize), getSizeWithDefault(pSize));
    }

    static Size getSizeWithDefault(String str) {
        return switch (str) {
            case "BIG" -> Size.BIG;
            case "MEDIUM" -> Size.MEDIUM;
            default -> Size.SMALL;
        };
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

    @Override
    public Size getBaggageSize() {
        return this.baggageSize;
    }

    @Override
    public String getBaggageSizeDescription() {
        return switch (this.mailSize) {
            case SMALL -> "Small size baggage";
            case MEDIUM -> "Medium size baggage";
            case BIG -> "Big size baggage";
        };
    }

    @Override
    public void stationAction() {
        this.setPackages();
        this.setBaggage();
    }

    @Override
    public void endRouteAction() {
        this.numBaggage = 0;
        this.numPackages = 0;
    }

    private void setBaggage() {
        Random rand = new Random();
        this.numBaggage = switch (this.baggageSize) {
            case SMALL -> rand.nextInt(100);
            case MEDIUM -> rand.nextInt(50);
            case BIG -> rand.nextInt(25);
        };
    }

    private void setPackages() {
        Random rand = new Random();
        this.numPackages = switch (this.mailSize) {
            case SMALL -> rand.nextInt(150);
            case MEDIUM -> rand.nextInt(75);
            case BIG -> rand.nextInt(30);
        };
    }

    @Override
    public String toString() {
        return String.format("Baggage Car. %s. %s. Max weight: %f. Amount of packages: %d. Amount of baggage: %d", this.getMailSizeDescription(), this.getBaggageSizeDescription(), this.maxGrossWeight, this.numPackages, this.numBaggage);
    }
}
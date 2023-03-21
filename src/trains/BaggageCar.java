package trains;

public class BaggageCar extends TrainCar implements IMailCar, IBaggageCar {
    private final Size mailSize;
    private final Size baggageSize;

    public BaggageCar(Size baggageSize, Size mailSize) {
        super(false, 26);
        this.mailSize = mailSize;
        this.baggageSize = baggageSize;
    }

    public BaggageCar() {
        this(Size.SMALL, Size.SMALL);
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
}
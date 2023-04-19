package trains;

import java.util.Scanner;

public class ToxicCar extends HeavyFreightCar {
    protected final Toxicity toxicity;

    public ToxicCar(Toxicity toxicity) {
        super(33);
        this.toxicity = toxicity;
    }

    public static ToxicCar createFromScanner(Scanner scanner) throws Exception {
        System.out.println("What is the toxicity level? HIGH, MEDIUM, LOW (default)");
        String type = scanner.nextLine();

        return new ToxicCar(getToxicityLevel(type));
    }

    public static Toxicity getToxicityLevel(String str) {
        return switch (str) {
            case "HIGH" -> Toxicity.HIGH;
            case "MEDIUM" -> Toxicity.MEDIUM;
            default -> Toxicity.LOW;
        };
    }

    public String getToxicity() {
        return switch (this.toxicity) {
            case LOW -> "Not very toxic";
            case MEDIUM -> "Pretty toxic";
            case HIGH -> "Very toxic, do not touch";
        };
    }

    @Override
    public String toString() {
        return String.format("Toxins Car #%d. Toxicity: %s", this.id, this.getToxicity());
    }
}

package trains;

public class MailCar extends TrainCar implements IMailCar {
	private final Size mailSize;

	public MailCar(Size size) {
		super(true, 25);
		this.mailSize = size;
	}

	public MailCar() {
		this(Size.SMALL);
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
}

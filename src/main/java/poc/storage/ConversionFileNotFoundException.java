package poc.storage;

public class ConversionFileNotFoundException extends ConversionException {
	private static final long serialVersionUID = 1L;

	public ConversionFileNotFoundException(String message) {
		super(message);
	}

	public ConversionFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
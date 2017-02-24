package resources;

public class InvalidMoveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	public InvalidMoveException() {
	}

	public InvalidMoveException(String message) {
		super(message);
	}

	public InvalidMoveException(Throwable cause) {
		super(cause);
	}

	public InvalidMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMoveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

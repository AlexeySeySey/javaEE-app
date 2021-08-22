package exception;

public final class NothingFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NothingFoundException() {
		//
	}

	public NothingFoundException(String message) {
		super(message);
	}

	public NothingFoundException(Throwable cause) {
		super(cause);
	}

	public NothingFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

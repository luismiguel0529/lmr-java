package wolox.training.exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package okon.ASE9.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String message) {
        super(message);
    }
}

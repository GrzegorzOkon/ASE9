package okon.ASE9.exception;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message) {
        super(message);
    }
}

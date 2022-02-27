package service;

public class InaccessibleFieldException extends RuntimeException {
    public InaccessibleFieldException(Throwable cause) {
        super(cause);
    }
}

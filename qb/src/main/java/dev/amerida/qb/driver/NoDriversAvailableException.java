package dev.amerida.qb.driver;

public class NoDriversAvailableException extends RuntimeException {
    public NoDriversAvailableException(String message) {
        super(message);
    }
}

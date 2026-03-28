package dev.am.resilience.exception;

public class PaymentNetworkException extends RuntimeException {
    public PaymentNetworkException(String message) {
        super(message);
    }
}

package dev.am.resilience.service;

import dev.am.resilience.exception.PaymentNetworkException;
import dev.am.resilience.exception.PaymentValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class PaymentService {
    private final Random random = new Random();
    private final AtomicInteger attemptCount = new AtomicInteger(0);

    @Retryable(
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            jitter = 100,
            includes = {PaymentNetworkException.class},
            excludes = {PaymentValidationException.class}
    )
    public String processPayment(String orderId, double amount) {
        int attempt = attemptCount.incrementAndGet();
        log.info("Attempt {} for payment of $ {} for order {}", attempt, amount, orderId);

        if (amount <= 0) {
            log.info("Validation error: Payment amount must be greater than zero. No retry will occur.");
            attemptCount.set(0);
            throw new PaymentValidationException("Payment amount must be greater than zero");
        }

        if (random.nextBoolean()) {
            log.info(">>> Attempt #{}: Network error: Unable to connect to payment gateway.", attempt);
            throw new PaymentNetworkException("Network error: Unable to connect to payment gateway");
        }

        return "Payment of $ " + amount + " for order " + orderId + " successful";
    }
}

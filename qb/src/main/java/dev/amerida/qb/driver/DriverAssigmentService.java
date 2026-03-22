package dev.amerida.qb.driver;

import dev.amerida.qb.order.Order;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DriverAssigmentService {
    private static final Logger log = LoggerFactory.getLogger(DriverAssigmentService.class);

    private final List<Driver> drivers = new ArrayList<>();
    private final RetryTemplate retryTemplate;
    private final Random random = new Random();

    public DriverAssigmentService() {
        // Configure RetryTemplate programmatically
        // This is useful when you need more dynamic control over retry behavior
        // new RetryTemplate() :: Implicitly uses RetryPolicy.withDefaults()
        // retryTemplate = new RetryTemplate();
        var retryPolicy = RetryPolicy.builder()
                .maxRetries(10)
                .delay(Duration.ofSeconds(2))
                .multiplier(1.5)
                .includes(NoDriversAvailableException.class)
                .build();
        this.retryTemplate = new RetryTemplate(retryPolicy);
    }

    public Driver assignDriver(Order order) throws RetryException {
        // Use AtomicInteger to track attempts
        AtomicInteger attempts = new AtomicInteger(0);

        return retryTemplate.execute(() -> {
            int currentAttempt = attempts.incrementAndGet();
            log.info("  Attempt #{} to find available driver", currentAttempt);

            // Simulate random driver availability (50% chance of success)
            if (random.nextDouble() > 0.5 || drivers.isEmpty()) {
                throw new NoDriversAvailableException("No drivers available in area. Will retry...");
            }

            // Assign a random available driver
            Driver assignedDriver = drivers.get(
                    random.nextInt(drivers.size())
            );
            log.info("✅ Driver {} assigned to order {}", assignedDriver.name(), order.id());

            return assignedDriver;
        });
    }

    @PostConstruct
    private void initializeDrivers() {
        drivers.addAll(List.of(
                new Driver("1", "Alex Johnson", 4.8),
                new Driver("2", "Maria Garcia", 4.9),
                new Driver("3", "James Wilson", 4.5),
                new Driver("4", "Sarah Chen", 4.7),
                new Driver("5", "Mike Roberts", 4.6)
        ));
    }
}

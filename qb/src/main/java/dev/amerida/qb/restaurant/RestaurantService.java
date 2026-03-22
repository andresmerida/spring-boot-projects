package dev.amerida.qb.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Service
public class RestaurantService {
    private static final Logger log = LoggerFactory.getLogger(RestaurantService.class);
    private final DataLoader dataLoader;

    private final Random random = new Random();

    public RestaurantService(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    /**
     * Get menu from partner restaurant API (simulates flaky external API).
     *
     * @Retryable configuration:
     * - default: All Exceptions & 3 retries
     * - maxAttempts: 4 (1 initial + 3 retries)
     * - includes: Only retry on RestaurantApiException
     * - backoff: Start with 1 second, multiply by 2 (exponential backoff)
     */
    @Retryable(
            maxRetries = 4,
            includes = RestaurantApiException.class,
            delay = 1100,
            multiplier = 2  // double the delay each retry
    )
    public List<MenuItem> getMenuFromPartner(String restaurantId) {
        log.info("Getting menu items for restaurant {}", restaurantId);

        // simulate flaky external API (40% failure rate)
        if (random.nextDouble() < 0.4) {
            log.warn("Failed to get menu items for restaurant {}, will retry again...", restaurantId);
            throw new RestaurantApiException("Failed to get menu items, API is temporarily unavailable...");
        }

        // simulate network latency
        simulateDelay(Duration.ofMillis(200));

        Restaurant restaurant = dataLoader.getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new RestaurantApiException("Restaurant not found: " + restaurantId);
        }

        List<MenuItem> menu = restaurant.menuItemIds().stream()
                .map(dataLoader::getMenuItem)
                .filter(menuItem -> menuItem != null && menuItem.available())
                .toList();

        log.info("Got {} menu items for restaurant {}", menu.size(), restaurantId);
        return menu;
    }

    public List<Restaurant> getRestaurants() {
        return dataLoader.getRestaurants().values().stream().toList();
    }

    private void simulateDelay(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

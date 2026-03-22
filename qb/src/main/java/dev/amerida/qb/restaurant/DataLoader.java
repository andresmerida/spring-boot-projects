package dev.amerida.qb.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final JsonMapper jsonMapper;

    private final Map<String, Restaurant> restaurantsMap = new HashMap<>();
    private final Map<String, MenuItem> menuItemsMap = new HashMap<>();

    public DataLoader(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    private void loadData() {
        try {
            // load restaurants
            List<Restaurant> restaurants = jsonMapper
                    .readValue(getClass().getResourceAsStream("/data/restaurants.json"), new TypeReference<>() {});
            restaurants.forEach(r -> restaurantsMap.put(r.id(), r));
            log.info("Loaded {} restaurants", restaurants.size());

            // load menu items
            List<MenuItem> menuItems = jsonMapper
                    .readValue(getClass().getResourceAsStream("/data/menu-items.json"), new TypeReference<>() {});
            menuItems.forEach(m -> menuItemsMap.put(m.id(),m));
            log.info("Loaded {} menu items", menuItems.size());
        } catch (JacksonException e) {
            log.error("Error loading data", e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data");
        loadData();
        log.info("Data loaded successfully");
    }

    // helper methods for outside classes to access data
    public Map<String, Restaurant> getRestaurants() {
        return restaurantsMap;
    }

    public Map<String, MenuItem> getMenuItems() {
        return menuItemsMap;
    }

    public Restaurant getRestaurant(String id) {
        return restaurantsMap.get(id);
    }

    public MenuItem getMenuItem(String id) {
        return menuItemsMap.get(id);
    }
}

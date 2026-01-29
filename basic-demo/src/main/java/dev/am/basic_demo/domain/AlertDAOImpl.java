package dev.am.basic_demo.domain;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class AlertDAOImpl implements AlertDAO {
    public final ConcurrentMap<String, Alert> store = new ConcurrentHashMap<>();

    public AlertDAOImpl() {}

    @Override
    public Optional<Alert> getById(String id) {
        return store.containsKey(id) ? Optional.of(store.get(id)) : Optional.empty();
    }

    @Override
    public List<Alert> getAll() {
        return store.values().stream().toList();
    }

    @Override
    public Alert save(Alert alert) {
        store.put(alert.getId(), alert);
        return alert;
    }

    @Override
    public Alert update(Alert alert) {
        if (store.containsKey(alert.getId())) {
            return store.put(alert.getId(), alert);
        } else {
            throw new RuntimeException("Alert not found with id: " + alert.getId());
        }
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }

    @PostConstruct
    public void init() {
        Alert alert = new Alert("1", "Hello World!");
        Alert alert2 = new Alert("2", "Hello World 2!");
        Alert alert3 = new Alert("3", "Hello World 3!");
        store.put(alert.getId(), alert);
        store.put(alert2.getId(), alert2);
        store.put(alert3.getId(), alert3);
    }
}

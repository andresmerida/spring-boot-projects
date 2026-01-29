package dev.am.basic_demo.domain;

import java.util.List;
import java.util.Optional;

interface AlertDAO {
    Optional<Alert> getById(String id);
    List<Alert> getAll();
    Alert save(Alert alert);
    Alert update(Alert alert);
    void deleteById(String id);
}

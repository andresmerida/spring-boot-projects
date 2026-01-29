package dev.am.basic_demo.domain;

import dev.am.basic_demo.dto.AlertRequest;
import dev.am.basic_demo.dto.AlertResponse;

import java.util.List;
import java.util.Optional;

public interface AlertService {
    Optional<AlertResponse> getById(String id);
    List<AlertResponse> getAll();
    AlertResponse save(AlertRequest request);
    AlertResponse update(AlertRequest request, String id);
    void deleteById(String id);
}

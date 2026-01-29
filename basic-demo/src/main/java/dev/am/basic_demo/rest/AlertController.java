package dev.am.basic_demo.rest;

import dev.am.basic_demo.domain.AlertService;
import dev.am.basic_demo.dto.AlertRequest;
import dev.am.basic_demo.dto.AlertResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    ResponseEntity<List<AlertResponse>> getAll() {
        return ResponseEntity.ok(alertService.getAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<AlertResponse> getById(String id) {
        return alertService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found for id: " + id));
    }

    @PostMapping
    ResponseEntity<AlertResponse> save(@RequestBody AlertRequest request) throws URISyntaxException {
        AlertResponse alertResponse = alertService.save(request);
        return ResponseEntity
                .created(new URI("/api/alerts/" + alertResponse.id()))
                .body(alertResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<AlertResponse> update(@RequestBody AlertRequest request, @PathVariable String id) {
        return ResponseEntity.ok(alertService.update(request, id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable String id) {
        alertService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

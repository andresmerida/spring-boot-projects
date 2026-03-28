package dev.am.resilience.controller;

import dev.am.resilience.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class HttpBinController {
    private static final Logger log = LoggerFactory.getLogger(HttpBinController.class);
    private final RestClient client;


    public HttpBinController(RestClient client) {
        this.client = client;
    }

    //    public HttpBinController(RestClient.Builder builder) {
//        this.client = builder
//                .baseUrl("https://httpbin.org")
//                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
//                    throw new HttpStatusCodeException(response.getStatusCode(), "Error calling " + request.getURI()) {};
//                })
//                .build();
//    }

    @GetMapping("/get")
    public String get() {
        return client.get()
                .uri("/get")
                .retrieve()
                .body(String.class);
    }

    @GetMapping("/get/status/{code}")
    public ResponseEntity<String> getStatusCode(@PathVariable Integer code) {
        ResponseEntity<Void> response = client.get()
                .uri("/status/{code}", code)
                .retrieve()
                .toBodilessEntity(); // defaultStatusHandler fires now

        return ResponseEntity.ok("Success: " + response.getStatusCode().value());
    }

//    @GetMapping("/get/status/{code}")
//    public ResponseEntity<String> getStatusCode(@PathVariable Integer code) {
//        ResponseEntity<Void> response = client.get()
//                .uri("/get/status/{code}", code)
//                .retrieve()
//                // The RestClient default behavior is to throw an exception on 4xx/5xx responses before your code ever runs.
//                .onStatus(HttpStatusCode::isError, (req, res) -> {}) // suppress default exception
//                .toBodilessEntity();
//
//        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
//        }
//
//        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
//        }
//
//        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
//        }
//
//        if (response.getStatusCode().is5xxServerError()) {
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Downstream error");
//        }
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return ResponseEntity.ok("Success: " + response.getStatusCode().value());
//        }
//
//        return ResponseEntity.status(response.getStatusCode()).body("Unexpected status");
//    }

    @Retryable(includes = ApiException.class, maxRetries = 3, delay = 1000L, multiplier = 2)
    @GetMapping("/unstable")
    public String getUnstable() {
        log.info("Attempting to call /unstable...");
        return client.get()
                .uri("/unstable")
                .retrieve()
                .body(String.class);
    }
}

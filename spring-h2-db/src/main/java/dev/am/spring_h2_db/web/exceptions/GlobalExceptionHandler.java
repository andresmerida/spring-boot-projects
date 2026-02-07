package dev.am.spring_h2_db.web.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Environment environment;

    @ExceptionHandler(PersonNotFoundException.class)
    ProblemDetail handle(PersonNotFoundException ex) {
        log.error("Person not found", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Person not found");
        problemDetail.setType(URI.create("https://example.com/problems/not-found"));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail unHandleException(Exception ex) {
        log.error("Unexpected error", ex);
        // Don't expose internal errors to the client in production
        String message = isDevMode() ? ex.getMessage() : "An unexpected error occurred";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, message);
        problemDetail.setTitle("Internal server error");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    private boolean isDevMode() {
        return environment.matchesProfiles("dev", "local");
    }
}

package dev.am.ss_jwt.web.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Environment environment;

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail badCredentialsException(BadCredentialsException ex) {
        log.warn("Bad credentials", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Bad credentials");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(AuthenticationException.class)
    ProblemDetail authenticationException(AuthenticationException ex) {
        log.warn("Authentication failed", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Authentication failed");
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

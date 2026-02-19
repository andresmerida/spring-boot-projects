package dev.am.jw2.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final Environment environment;

    @ExceptionHandler(UsernameNotFoundException.class)
    ProblemDetail usernameNotFoundException(UsernameNotFoundException ex) {
        log.warn("User not found", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("User not found");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    ProblemDetail authorizationDeniedException(AuthorizationDeniedException ex) {
        log.warn("Authorization denied", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
        problemDetail.setTitle("Forbidden");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail accessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied");
        problemDetail.setTitle("Forbidden");
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }

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

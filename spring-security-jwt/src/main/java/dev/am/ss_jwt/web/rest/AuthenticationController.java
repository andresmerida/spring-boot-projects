package dev.am.ss_jwt.web.rest;

import dev.am.ss_jwt.config.JwtService;
import dev.am.ss_jwt.domain.CustomUser;
import dev.am.ss_jwt.dto.LoginResponse.LoginResponse;
import dev.am.ss_jwt.dto.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginUserRequest loginUserRequest) {
        // Create an unauthenticated token with raw credentials
        // NOTE: do not hash the password here! Pass it raw
        UsernamePasswordAuthenticationToken unauthenticatedToken =
                new UsernamePasswordAuthenticationToken(loginUserRequest.username(), loginUserRequest.password());
        // Delegate to AuthenticationManager, it will automatically use the correct encoder based on the stored hash prefix
        Authentication authentication = authenticationManager.authenticate(unauthenticatedToken);
        CustomUser user = (CustomUser) authentication.getPrincipal();

        assert user != null;
        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user)));
    }
}

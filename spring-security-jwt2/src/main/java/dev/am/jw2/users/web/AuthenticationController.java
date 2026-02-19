package dev.am.jw2.users.web;

import dev.am.jw2.common.JwtService;
import dev.am.jw2.dto.LoginResponse;
import dev.am.jw2.dto.LoginUserRequest;
import dev.am.jw2.users.domain.CustomUserDetails;
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
        var unauthenticatedToken =
                new UsernamePasswordAuthenticationToken(loginUserRequest.username(), loginUserRequest.password());

        // Delegate to AuthenticationManager, it will automatically use the correct encoder based on the stored hash prefix
        Authentication authentication = authenticationManager.authenticate(unauthenticatedToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        assert user != null;
        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(user)));
    }

}

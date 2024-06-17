package com.moviki.backend.controller;

import com.moviki.backend.dto.LoginRequest;
import com.moviki.backend.dto.RegisterRequest;
import com.moviki.backend.dto.RegisterResponse;
import com.moviki.backend.model.Client;
import com.moviki.backend.dto.LoginResponse;
import com.moviki.backend.service.AuthenticationService;
import com.moviki.backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        Client registeredClient = authenticationService.register(registerRequest);
        RegisterResponse registerResponse = new RegisterResponse(registeredClient.getName(), registeredClient.getEmail(), List.copyOf(registeredClient.getRoles()));
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
        Client authenticatedClient = authenticationService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedClient);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), List.copyOf(authenticatedClient.getRoles()));
        return ResponseEntity.ok(loginResponse);
    }
}

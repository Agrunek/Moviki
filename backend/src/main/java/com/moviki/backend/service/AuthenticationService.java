package com.moviki.backend.service;

import com.moviki.backend.dto.LoginRequest;
import com.moviki.backend.dto.RegisterRequest;
import com.moviki.backend.model.Role;
import com.moviki.backend.model.Client;
import com.moviki.backend.repository.RoleRepository;
import com.moviki.backend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Client register(RegisterRequest registerRequest) {
        Optional<Role> optionalRole = roleRepository.findByName("CLIENT");
        Role role = optionalRole.orElseThrow(() -> new RuntimeException("Role not found!"));

        Client client = new Client().withName(registerRequest.getName()).withEmail(registerRequest.getEmail()).withPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        client.getRoles().add(role);

        return clientRepository.save(client);
    }

    @Transactional
    public Client authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return clientRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Client not found!"));
    }
}

package com.moviki.backend.controller;

import com.moviki.backend.dto.ClientResponse;
import com.moviki.backend.model.Client;
import com.moviki.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ClientResponse> getClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = (Client) authentication.getPrincipal();
        ClientResponse clientResponse = new ClientResponse(currentClient.getName(), currentClient.getEmail(), currentClient.getProfilePicturePath(), currentClient.getCreatedAt(), List.copyOf(currentClient.getRoles()));
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClientResponse>> getClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientResponse> clientResponse = clients.stream().map(client -> new ClientResponse(client.getName(), client.getEmail(), client.getProfilePicturePath(), client.getCreatedAt(), List.copyOf(client.getRoles()))).toList();
        return ResponseEntity.ok(clientResponse);
    }
}

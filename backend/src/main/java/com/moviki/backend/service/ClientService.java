package com.moviki.backend.service;

import com.moviki.backend.model.Client;
import com.moviki.backend.model.Role;
import com.moviki.backend.repository.ClientRepository;
import com.moviki.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client assignRoleToClient(String clientName, String roleName) {
        if (roleName.equals("ADMIN")) {
            throw new RuntimeException("Admin role cannot be assigned!");
        }

        Optional<Client> optionalClient = clientRepository.findByName(clientName);
        Client client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found!"));

        if (client.getRoles().stream().noneMatch(role -> role.getName().equals(roleName))) {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            Role role = optionalRole.orElseThrow(() -> new RuntimeException("Role not found!"));
            client.getRoles().add(role);
        }

        return clientRepository.save(client);
    }

    @Transactional
    public Client removeRoleFromClient(String clientName, String roleName) {
        if (roleName.equals("ADMIN")) {
            throw new RuntimeException("Admin role cannot be removed!");
        }

        Optional<Client> optionalClient = clientRepository.findByName(clientName);
        Client client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found!"));

        client.getRoles().removeIf(role -> role.getName().equals(roleName));

        return clientRepository.save(client);
    }
}

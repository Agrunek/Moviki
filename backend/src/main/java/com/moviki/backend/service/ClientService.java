package com.moviki.backend.service;

import com.moviki.backend.model.Client;
import com.moviki.backend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}

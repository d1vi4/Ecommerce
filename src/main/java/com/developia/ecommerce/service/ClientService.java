package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.repository.ClientRepository;
import com.developia.ecommerce.dto.ClientRegistrationRequest;
import com.developia.ecommerce.dto.ClientResponse;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean verifyPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);
    }

    @Transactional
    public ClientEntity registerClient(ClientRegistrationRequest request) {
        if (clientRepository.existsByUsername(request.getUsername())) {
            throw new CustomExceptions.DuplicateResourceException("Istifadeci", "username", request.getUsername());
        }

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new CustomExceptions.DuplicateResourceException("Istifadeci", "email", request.getEmail());
        }

        ClientEntity client = new ClientEntity();
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setEmail(request.getEmail());
        client.setUsername(request.getUsername());
        
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setRole("CLIENT");

        return clientRepository.save(client);
    }
    
    public ClientResponse getClientProfileByUsername(String username) {
        ClientEntity client = getClientEntityByUsername(username);

        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setSurname(client.getSurname());
        response.setEmail(client.getEmail());
        response.setUsername(client.getUsername());
        
        return response;
    }

    public ClientEntity getClientEntityByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Istifadeci", "username", username));
    }
}
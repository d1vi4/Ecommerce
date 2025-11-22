package com.developia.ecommerce.controller;

import com.developia.ecommerce.dto.AuthResponse;
import com.developia.ecommerce.dto.ClientLoginRequest;
import com.developia.ecommerce.dto.ClientRegistrationRequest;
import com.developia.ecommerce.dto.ClientResponse;
import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.service.ClientService;
import com.developia.ecommerce.exception.CustomExceptions;
import com.developia.ecommerce.config.JwtTokenProvider; 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public ClientController(ClientService clientService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private String checkAuthAndGetUsername(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomExceptions.InvalidCredentialsException("Token yoxdur.");
        }
        String token = authHeader.substring(7);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomExceptions.InvalidCredentialsException("Token vaxti bitib veya yanlisdir.");
        }
        String username = jwtTokenProvider.getUsernameFromJwt(token);
        if (username == null) {
            throw new CustomExceptions.InvalidCredentialsException("Istifadeci tapilmadi.");
        }
        return username;
    }


    @PostMapping
    public ResponseEntity<String> registerClient(@Validated @RequestBody ClientRegistrationRequest request) {
        clientService.registerClient(request);
        return new ResponseEntity<>("Qeydiyyat ugurlu oldu. Indi daxil ola bilersiniz.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginClient(@Validated @RequestBody ClientLoginRequest request) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        ClientEntity client = clientService.getClientEntityByUsername(request.getUsername());
        ClientResponse clientDetails = clientService.getClientProfileByUsername(client.getUsername());

        return new ResponseEntity<>(new AuthResponse(
                clientDetails.getId(),
                clientDetails.getUsername(),
                client.getRole(),
                token
        ), HttpStatus.OK);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<ClientResponse> getProfile(@RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ClientResponse response = clientService.getClientProfileByUsername(username);
        return ResponseEntity.ok(response);
    }
}
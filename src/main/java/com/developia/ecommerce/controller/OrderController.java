package com.developia.ecommerce.controller;

import com.developia.ecommerce.dto.OrderRequest;
import com.developia.ecommerce.dto.OrderResponse;
import com.developia.ecommerce.service.OrderService;
import com.developia.ecommerce.service.ClientService;
import com.developia.ecommerce.exception.CustomExceptions;
import com.developia.ecommerce.config.JwtTokenProvider; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final JwtTokenProvider jwtTokenProvider;

    public OrderController(OrderService orderService, ClientService clientService, JwtTokenProvider jwtTokenProvider) {
        this.orderService = orderService;
        this.clientService = clientService;
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
    public ResponseEntity<OrderResponse> sifarisYarat(
            @Validated @RequestBody OrderRequest request,
            @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        OrderResponse response = orderService.sifarisYarat(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        List<OrderResponse> history = orderService.sifarisTarixcesiniAl(username);
        return ResponseEntity.ok(history);
    }
}
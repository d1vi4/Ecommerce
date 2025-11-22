package com.developia.ecommerce.controller;

import com.developia.ecommerce.dto.ProductRequest;
import com.developia.ecommerce.dto.ProductResponse;
import com.developia.ecommerce.dto.CategoryResponse;
import com.developia.ecommerce.service.ProductService;
import com.developia.ecommerce.service.ClientService;
import com.developia.ecommerce.service.CategoryService;
import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.exception.CustomExceptions;
import com.developia.ecommerce.config.JwtTokenProvider; 
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ClientService clientService;
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductController(ProductService productService, ClientService clientService, CategoryService categoryService, JwtTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.clientService = clientService;
        this.categoryService = categoryService;
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

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductResponse> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Validated @RequestBody ProductRequest request,
            @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ClientEntity seller = clientService.getClientEntityByUsername(username);
        ProductResponse response = productService.createProduct(request, seller);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Validated @RequestBody ProductRequest request,
            @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ClientEntity seller = clientService.getClientEntityByUsername(username);
        ProductResponse response = productService.updateProduct(id, request, seller);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id, @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ClientEntity seller = clientService.getClientEntityByUsername(username);
        productService.deleteProduct(id, seller);
    }
}
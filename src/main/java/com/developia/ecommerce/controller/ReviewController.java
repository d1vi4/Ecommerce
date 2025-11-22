package com.developia.ecommerce.controller;

import com.developia.ecommerce.dto.ReviewRequestDTO;
import com.developia.ecommerce.dto.ReviewResponseDTO;
import com.developia.ecommerce.service.ReviewService;
import com.developia.ecommerce.config.JwtTokenProvider;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;

    public ReviewController(ReviewService reviewService, JwtTokenProvider jwtTokenProvider) {
        this.reviewService = reviewService;
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
    public ResponseEntity<ReviewResponseDTO> addReview(
            @Validated @RequestBody ReviewRequestDTO request,
            @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ReviewResponseDTO response = reviewService.addReview(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewResponseDTO>> getReviewsByProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ReviewResponseDTO> reviews = reviewService.getReviewsByProduct(productId, page, size);
        return ResponseEntity.ok(reviews);
    }
}
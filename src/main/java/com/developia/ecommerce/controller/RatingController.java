package com.developia.ecommerce.controller;

import com.developia.ecommerce.dto.RatingRequestDTO;
import com.developia.ecommerce.service.RatingService;
import com.developia.ecommerce.config.JwtTokenProvider;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final JwtTokenProvider jwtTokenProvider;

    public RatingController(RatingService ratingService, JwtTokenProvider jwtTokenProvider) {
        this.ratingService = ratingService;
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
    public ResponseEntity<Void> addRating(
            @Validated @RequestBody RatingRequestDTO request,
            @RequestHeader(name = "Authorization") String authHeader) {
        String username = checkAuthAndGetUsername(authHeader);
        ratingService.addOrUpdateRating(request, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
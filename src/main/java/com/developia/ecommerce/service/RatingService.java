package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.entity.RatingEntity;
import com.developia.ecommerce.repository.RatingRepository;
import com.developia.ecommerce.dto.RatingRequestDTO;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final ClientService clientService;

    public RatingService(RatingRepository ratingRepository, ProductService productService, ClientService clientService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Transactional
    public void addOrUpdateRating(RatingRequestDTO request, String username) {
        ProductEntity product = productService.findProductEntityById(request.getProductId());
        ClientEntity client = clientService.getClientEntityByUsername(username);
        
        Optional<RatingEntity> existingRating = ratingRepository.findByClientAndProduct(client, product);

        if (existingRating.isPresent()) {
          
            throw new CustomExceptions.DuplicateResourceException("Reytinq", "Məhsul ID", request.getProductId());
        }

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setProduct(product);
        ratingEntity.setClient(client);
        ratingEntity.setRating(request.getRating());

        ratingRepository.save(ratingEntity);

        productService.updateAverageRating(request.getProductId());
    }
}
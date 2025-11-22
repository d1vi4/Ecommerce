package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.entity.ReviewEntity;
import com.developia.ecommerce.repository.ReviewRepository;
import com.developia.ecommerce.dto.ReviewRequestDTO;
import com.developia.ecommerce.dto.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ClientService clientService;

    public ReviewService(ReviewRepository reviewRepository, ProductService productService, ClientService clientService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Transactional
    public ReviewResponseDTO addReview(ReviewRequestDTO request, String username) {
        ProductEntity product = productService.findProductEntityById(request.getProductId());
        ClientEntity client = clientService.getClientEntityByUsername(username);

        ReviewEntity review = new ReviewEntity();
        review.setProduct(product);
        review.setClient(client);
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());

        return mapToResponse(reviewRepository.save(review));
    }

    public Page<ReviewResponseDTO> getReviewsByProduct(Long productId, int page, int size) {
        ProductEntity product = productService.findProductEntityById(productId);
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findAllByProduct(product, pageable)
                .map(this::mapToResponse);
    }

    private ReviewResponseDTO mapToResponse(ReviewEntity entity) {
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setComment(entity.getComment());
        response.setClientUsername(entity.getClient().getUsername());
        response.setReviewDate(entity.getReviewDate());
        return response;
    }
}
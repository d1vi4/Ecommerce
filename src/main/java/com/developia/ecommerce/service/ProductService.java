package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.repository.ProductRepository;
import com.developia.ecommerce.dto.ProductRequest;
import com.developia.ecommerce.dto.ProductResponse;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService; 

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request, ClientEntity seller) {
        ProductEntity product = new ProductEntity();

        product.setCategory(categoryService.findCategoryById(request.getCategoryId()));
        
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setSeller(seller);

        return mapToResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request, ClientEntity seller) {
        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Mehsul", "id", productId));

       
        if (!existingProduct.getSeller().getId().equals(seller.getId())) {
            throw new CustomExceptions.InvalidCredentialsException();
        }

        existingProduct.setCategory(categoryService.findCategoryById(request.getCategoryId()));
        existingProduct.setTitle(request.getTitle());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setImageUrl(request.getImageUrl());

        return mapToResponse(productRepository.save(existingProduct));
    }
    

    public List<ProductResponse> getProductsBySeller(ClientEntity seller) {
        return productRepository.findAllBySeller(seller).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public Page<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public void deleteProduct(Long productId, ClientEntity seller) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Mehsul", "id", productId));

       
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new CustomExceptions.InvalidCredentialsException(); 
        }

        productRepository.delete(product);
    }


    private ProductResponse mapToResponse(ProductEntity entity) {
        ProductResponse response = new ProductResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setPrice(entity.getPrice());
        response.setStock(entity.getStock());
        response.setImageUrl(entity.getImageUrl());
        response.setCategoryName(entity.getCategory().getName());
        response.setSellerUsername(entity.getSeller().getUsername());
        return response;
    }
    
    public ProductEntity findProductEntityById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Mehsul", "id", productId));
    }
}
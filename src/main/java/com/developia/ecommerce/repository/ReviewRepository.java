package com.developia.ecommerce.repository;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Page<ReviewEntity> findAllByProduct(ProductEntity product, Pageable pageable);

    boolean existsByClientAndProduct(ClientEntity client, ProductEntity product);
}
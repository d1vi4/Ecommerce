package com.developia.ecommerce.repository;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findAllByProduct(ProductEntity product);
    Optional<RatingEntity> findByClientAndProduct(ClientEntity client, ProductEntity product);
}
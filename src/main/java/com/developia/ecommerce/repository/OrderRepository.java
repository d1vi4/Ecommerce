package com.developia.ecommerce.repository;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByClient(ClientEntity client);

    @Query("SELECT DISTINCT o FROM OrderEntity o JOIN o.items i WHERE i.product.seller.id = :sellerId")
    List<OrderEntity> findOrdersBySellerId(@Param("sellerId") Long sellerId);
}
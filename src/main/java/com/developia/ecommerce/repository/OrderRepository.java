package com.developia.ecommerce.repository;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByClient(ClientEntity client);
}
package com.developia.ecommerce.repository;

import com.developia.ecommerce.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
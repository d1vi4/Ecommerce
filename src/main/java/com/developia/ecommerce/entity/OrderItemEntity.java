package com.developia.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sifaris_mehsullari")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sifaris_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mehsul_id", nullable = false)
    private ProductEntity product;

    @Column(name = "say", nullable = false)
    private Integer quantity;

    @Column(name = "sifarisdeki_qiymet", nullable = false)
    private BigDecimal priceAtOrder;
}
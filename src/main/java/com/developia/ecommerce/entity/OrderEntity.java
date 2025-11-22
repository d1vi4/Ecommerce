package com.developia.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sifarisler")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musteri_id", nullable = false)
    private ClientEntity client;

    @Column(name = "sifaris_tarixi")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "yekun_qiymet", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "gonderis_unvani", nullable = false)
    private String shippingAddress;

    @Column(name = "sifaris_statusu", nullable = false)
    private String status = "Qebul edildi";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
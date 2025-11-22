package com.developia.ecommerce.entity;

import jakarta.persistence.*; 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mehsullar")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basliq", nullable = false)
    private String title;

    @Column(name = "aciqlama", columnDefinition = "TEXT")
    private String description;

    @Column(name = "qiymet", nullable = false)
    private BigDecimal price;

    @Column(name = "stok_sayi", nullable = false)
    private Integer stock;

    @Column(name = "sekil_url")
    private String imageUrl;

    @Column(name = "yaradilma_tarixi")
    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kateqoriya_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "satici_id", nullable = false)
    private ClientEntity seller;
}
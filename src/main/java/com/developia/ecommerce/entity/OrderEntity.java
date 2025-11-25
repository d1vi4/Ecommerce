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

    @Column(name = "sifaris_statusu", nullable = false)
    private String status = "Qebul edildi";

    @Column(name = "ad")
    private String firstName;

    @Column(name = "soyad")
    private String lastName;

    @Column(name = "region_id")
    private Long stateId;

    @Column(name = "seher")
    private String city;

    @Column(name = "unvan")
    private String address;

    @Column(name = "poct_indeksi")
    private Integer zip;

    @Column(name = "telefon")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "raziliq")
    private Boolean isAgree;

    @Column(name = "kart_nomresi")
    private Long cardNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
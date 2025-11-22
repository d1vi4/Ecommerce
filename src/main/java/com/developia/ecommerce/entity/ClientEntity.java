package com.developia.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "musteriler")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad", nullable = false)
    private String name;

    @Column(name = "soyad", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "istifadeci_adi", nullable = false, unique = true)
    private String username;

    @Column(name = "sifre", nullable = false)
    private String password;

    @Column(name = "qeydiyyat_tarixi")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "roll")
    private String role = "CLIENT";
}
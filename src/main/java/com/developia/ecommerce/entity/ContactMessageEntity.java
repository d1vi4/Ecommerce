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
@Table(name = "elaqe_mesajlari")
public class ContactMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefon")
    private String phone;

    @Column(name = "movzu", nullable = false)
    private String subject;

    @Column(name = "mesaj", columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "gonderilme_tarixi")
    private LocalDateTime sentDate = LocalDateTime.now();
}
package com.developia.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Basliq bos ola bilmez.")
    private String title;

    private String description;

    @NotNull(message = "Qiymet bos ola bilmez.")
    @DecimalMin(value = "0.01", message = "Qiymet 0-dan boyuk olmalidir.")
    private BigDecimal price;

    @NotNull(message = "Stok sayi bos ola bilmez.")
    @Min(value = 0, message = "Stok sayi menfi ola bilmez.")
    private Integer stock;

    private String imageUrl;

    @NotNull(message = "Kateqoriya ID bos ola bilmez.")
    private Long categoryId;
}
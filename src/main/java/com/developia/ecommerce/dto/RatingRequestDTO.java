package com.developia.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDTO {

    @NotNull(message = "Mehsul ID bos ola bilmez.")
    private Long productId;

    @NotNull(message = "Reytinq bos ola bilmez.")
    @Min(value = 1, message = "Reytinq 1-den kicik olmamalidir.")
    @Max(value = 5, message = "Reytinq 5-den boyuk olmamalidir.")
    private Integer rating;
}
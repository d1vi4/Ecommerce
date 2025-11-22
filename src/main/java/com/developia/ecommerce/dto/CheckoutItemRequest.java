package com.developia.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutItemRequest {

    @NotNull(message = "Mehsul ID bos ola bilmez.")
    private Long productId;

    @NotNull(message = "Say bos ola bilmez.")
    @Min(value = 1, message = "Say en az 1 olmalidir.")
    private Integer quantity;
}
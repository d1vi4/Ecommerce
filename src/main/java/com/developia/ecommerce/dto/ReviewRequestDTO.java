package com.developia.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    @NotNull(message = "Mehsul ID bos ola bilmez.")
    private Long productId;

    private String title;

    @NotBlank(message = "Serh bos ola bilmez.")
    private String comment;
}
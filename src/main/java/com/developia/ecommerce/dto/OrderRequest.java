package com.developia.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Gonderis unvani bos ola bilmez.")
    private String shippingAddress;

    @NotEmpty(message = "Sifaris mehsulsuz ola bilmez.")
    @Valid
    private List<CheckoutItemRequest> items;
}
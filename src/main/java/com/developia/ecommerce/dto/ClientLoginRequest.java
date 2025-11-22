package com.developia.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientLoginRequest {

    @NotBlank(message = "Istifadeci adi daxil edin.")
    private String username;

    @NotBlank(message = "Sifre daxil edin.")
    private String password;
}
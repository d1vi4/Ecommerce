package com.developia.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationRequest {

    @NotBlank(message = "Ad bos ola bilmez.")
    private String name;

    @NotBlank(message = "Soyad bos ola bilmez.")
    private String surname;

    @NotBlank(message = "Email bos ola bilmez.")
    @Email(message = "Email format duzgun deyil.")
    private String email;

    @NotBlank(message = "Istifadeci adi bos ola bilmez.")
    private String username;

    @NotBlank(message = "Sifre bos ola bilmez.")
    @Size(min = 6, message = "Sifre en az 6 simvol olmalidir.")
    private String password;
}
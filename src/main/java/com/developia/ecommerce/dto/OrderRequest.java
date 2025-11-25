package com.developia.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String firstName;
    private String lastName;
    private Long stateId;
    private String city;
    private String address;
    private Integer zip;
    private String phone;
    private String email;
    private Boolean isAgree;
    private Long cardNumber;
    private Integer expirationMonth;
    private Integer expirationYear;
    private Integer cardSecurityCode;

    @NotNull
    @Valid
    private List<CheckoutItemRequest> products;
}
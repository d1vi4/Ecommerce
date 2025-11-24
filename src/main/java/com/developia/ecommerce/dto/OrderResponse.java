package com.developia.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private String productImageUrl;
        private Integer quantity;
        private BigDecimal price;
    }
}
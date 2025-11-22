package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.OrderEntity;
import com.developia.ecommerce.entity.OrderItemEntity;
import com.developia.ecommerce.entity.ProductEntity;
import com.developia.ecommerce.repository.OrderItemRepository;
import com.developia.ecommerce.repository.OrderRepository;
import com.developia.ecommerce.dto.CheckoutItemRequest;
import com.developia.ecommerce.dto.OrderRequest;
import com.developia.ecommerce.dto.OrderResponse;
import com.developia.ecommerce.exception.CustomExceptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final ClientService clientService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductService productService, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Transactional
    public OrderResponse sifarisYarat(OrderRequest request, String username) {
        ClientEntity client = clientService.getClientEntityByUsername(username);

        OrderEntity order = new OrderEntity();
        order.setClient(client);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus("Qebul edildi");

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();

        for (CheckoutItemRequest itemRequest : request.getItems()) {
            ProductEntity product = productService.findProductEntityById(itemRequest.getProductId());
            Integer requestedQuantity = itemRequest.getQuantity();

            if (product.getStock() < requestedQuantity) {
                throw new CustomExceptions.InsufficientStockException(
                        product.getTitle(), requestedQuantity, product.getStock()
                );
            }

            BigDecimal itemPriceAtOrder = product.getPrice();
            BigDecimal itemSubtotal = itemPriceAtOrder.multiply(BigDecimal.valueOf(requestedQuantity));
            totalAmount = totalAmount.add(itemSubtotal);

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(requestedQuantity);
            orderItem.setPriceAtOrder(itemPriceAtOrder);
            orderItems.add(orderItem);

            product.setStock(product.getStock() - requestedQuantity);
        }

        order.setTotalPrice(totalAmount);
        OrderEntity savedOrder = orderRepository.save(order);
        
        for (OrderItemEntity item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> sifarisTarixcesiniAl(String username) {
        ClientEntity client = clientService.getClientEntityByUsername(username);
        return orderRepository.findAllByClient(client).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(OrderEntity entity) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(entity.getId());
        response.setOrderDate(entity.getOrderDate());
        response.setTotalPrice(entity.getTotalPrice());
        response.setStatus(entity.getStatus());
        return response;
    }
}
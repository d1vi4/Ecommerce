package com.developia.ecommerce.service;

import com.developia.ecommerce.entity.ClientEntity;
import com.developia.ecommerce.entity.OrderEntity;
import com.developia.ecommerce.entity.OrderItemEntity;
import com.developia.ecommerce.entity.ProductEntity;
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
    private final ProductService productService;
    private final ClientService clientService;

    public OrderService(OrderRepository orderRepository, ProductService productService, ClientService clientService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.clientService = clientService;
    }

    @Transactional
    public OrderResponse sifarisYarat(OrderRequest request, String username) {
        ClientEntity client = clientService.getClientEntityByUsername(username);

        OrderEntity order = new OrderEntity();
        order.setClient(client);
        order.setStatus("Qebul edildi");

        order.setFirstName(request.getFirstName());
        order.setLastName(request.getLastName());
        order.setStateId(request.getStateId());
        order.setCity(request.getCity());
        order.setAddress(request.getAddress());
        order.setZip(request.getZip());
        order.setPhone(request.getPhone());
        order.setEmail(request.getEmail());
        order.setIsAgree(request.getIsAgree());
        order.setCardNumber(request.getCardNumber());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();

        for (CheckoutItemRequest itemRequest : request.getProducts()) {
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
        order.setItems(orderItems);

        OrderEntity savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> sifarisTarixcesiniAl(String username) {
        ClientEntity client = clientService.getClientEntityByUsername(username);
        return orderRepository.findAllByClient(client).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findOrdersBySeller(Long sellerId) {
        return orderRepository.findOrdersBySellerId(sellerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse mapToResponse(OrderEntity entity) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(entity.getId());
        response.setOrderDate(entity.getOrderDate());
        response.setTotalPrice(entity.getTotalPrice());
        response.setStatus(entity.getStatus());

        if (entity.getItems() != null) {
            List<OrderResponse.OrderItemDTO> itemDTOs = entity.getItems().stream()
                .map(item -> new OrderResponse.OrderItemDTO(
                    item.getProduct().getId(),
                    item.getProduct().getTitle(),
                    item.getProduct().getImageUrl(),
                    item.getQuantity(),
                    item.getPriceAtOrder()
                ))
                .collect(Collectors.toList());
            response.setItems(itemDTOs);
        }
        
        return response;
    }
    
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}
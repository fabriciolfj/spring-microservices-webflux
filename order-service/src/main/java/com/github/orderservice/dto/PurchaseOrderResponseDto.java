package com.github.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseOrderResponseDto {

    private Integer orderId;
    private Integer userId;
    private String productId;
    private Integer amount;
    private OrderStatus status;
}

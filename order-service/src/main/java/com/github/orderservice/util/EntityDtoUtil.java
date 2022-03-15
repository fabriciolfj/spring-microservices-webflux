package com.github.orderservice.util;

import com.github.orderservice.dto.PurchaseOrderResponseDto;
import com.github.orderservice.dto.RequestContext;
import com.github.orderservice.dto.TransactionRequestDto;
import com.github.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {

    private EntityDtoUtil() { }

    public static void setTransactionRequest(final RequestContext context) {
        final TransactionRequestDto requestDto = TransactionRequestDto.builder()
                .userId(context.getPurchaseOrderRequestDto().getUserId())
                .amount(context.getProductDTO().getPrice())
                .build();

        context.setTransactionRequestDto(requestDto);
    }

    public static PurchaseOrder getPurchaseOrder(final RequestContext context) {
        return PurchaseOrder.builder()
                .userId(context.getTransactionResponseDto().getUserId())
                .amount(context.getProductDTO().getPrice())
                .productId(context.getProductDTO().getId())
                .status(context.getStatus())
                .build();
    }

    public static PurchaseOrderResponseDto toPurchaseOrderResponse(final PurchaseOrder purchaseOrder) {
        return PurchaseOrderResponseDto
                .builder()
                .orderId(purchaseOrder.getId())
                .amount(purchaseOrder.getAmount())
                .productId(purchaseOrder.getProductId())
                .status(purchaseOrder.getStatus())
                .userId(purchaseOrder.getUserId())
                .build();
    }
}

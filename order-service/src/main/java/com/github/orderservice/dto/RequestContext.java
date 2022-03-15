package com.github.orderservice.dto;

import lombok.Data;

@Data
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDTO productDTO;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }

    public OrderStatus getStatus() {
        final TransactionStatus status = transactionResponseDto.getStatus();
        return status.equals(TransactionStatus.APPROVED) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
    }
}

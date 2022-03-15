package com.github.orderservice.service;

import com.github.orderservice.client.ProductClient;
import com.github.orderservice.client.UserClient;
import com.github.orderservice.dto.PurchaseOrderRequestDto;
import com.github.orderservice.dto.PurchaseOrderResponseDto;
import com.github.orderservice.dto.RequestContext;
import com.github.orderservice.dto.TransactionRequestDto;
import com.github.orderservice.repository.PurchaseOrderRepository;
import com.github.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private final PurchaseOrderRepository purchaseOrderRepository;

    /*public Mono<PurchaseOrderResponseDto> processOrderZip(final Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                .doOnNext(EntityDtoUtil::setTransactionRequest)
                .zipWhen(e -> userClient.authorizeTransaction(e.getTransactionRequestDto()))
                .map(t -> EntityDtoUtil.getPurchaseOrder(t.getT1()))
                .map(EntityDtoUtil::toPurchaseOrderResponse);

    }*/

    public Mono<PurchaseOrderResponseDto> processOrder(final Mono<PurchaseOrderRequestDto> requestDto) {
        return requestDto.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequest)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(purchaseOrderRepository::save)
                .map(EntityDtoUtil::toPurchaseOrderResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> userRequestResponse(final RequestContext requestContext) {
        return this.userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> productRequestResponse(final RequestContext requestContext) {
        return this.productClient.getProductById(requestContext.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(requestContext::setProductDTO)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(requestContext);
    }
}

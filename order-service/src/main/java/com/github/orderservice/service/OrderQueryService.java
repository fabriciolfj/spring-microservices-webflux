package com.github.orderservice.service;

import com.github.orderservice.dto.PurchaseOrderResponseDto;
import com.github.orderservice.repository.PurchaseOrderRepository;
import com.github.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(final int userId) {
        return Flux.fromStream(purchaseOrderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::toPurchaseOrderResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }
}

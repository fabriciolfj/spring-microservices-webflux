package com.github.userservice.controller;

import com.github.userservice.dto.TransactionRequestDto;
import com.github.userservice.dto.TransactionResponseDto;
import com.github.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody final Mono<TransactionRequestDto> dtoMono) {
        return dtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping("/{id}")
    public Flux<TransactionResponseDto> getAllByUser(@PathVariable("id") final int id) {
        return transactionService.findAllByUser(id);
    }
}

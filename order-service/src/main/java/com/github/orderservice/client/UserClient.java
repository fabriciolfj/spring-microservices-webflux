package com.github.orderservice.client;

import com.github.orderservice.dto.TransactionRequestDto;
import com.github.orderservice.dto.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") final String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(final TransactionRequestDto requestDto) {
        return webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }
}

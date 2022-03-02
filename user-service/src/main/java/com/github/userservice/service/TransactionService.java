package com.github.userservice.service;

import com.github.userservice.Util.EntityDtoUtil;
import com.github.userservice.dto.TransactionRequestDto;
import com.github.userservice.dto.TransactionResponseDto;
import com.github.userservice.dto.TransactionStatus;
import com.github.userservice.repository.UserRepository;
import com.github.userservice.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    @Transactional
    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(v -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(userTransactionRepository::save)
                .map(e -> EntityDtoUtil.toDto(e, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<TransactionResponseDto> findAllByUser(final int userId) {
        return userTransactionRepository.findByUserId(userId)
                .map(v -> EntityDtoUtil.toDto(v, TransactionStatus.APPROVED));
    }
}

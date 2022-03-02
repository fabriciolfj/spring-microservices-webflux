package com.github.userservice.Util;

import com.github.userservice.dto.TransactionRequestDto;
import com.github.userservice.dto.TransactionResponseDto;
import com.github.userservice.dto.TransactionStatus;
import com.github.userservice.dto.UserDto;
import com.github.userservice.entity.User;
import com.github.userservice.entity.UserTransaction;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    private EntityDtoUtil () { }

    public static UserDto toDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .balance(user.getBalance())
                .name(user.getName())
                .build();
    }

    public static User toEntity(final UserDto dto) {
        return User
                .builder()
                .balance(dto.getBalance())
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static UserTransaction toEntity(final TransactionRequestDto dto) {
        return UserTransaction
                .builder()
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public static TransactionResponseDto toDto(final UserTransaction userTransaction, final TransactionStatus status) {
        return TransactionResponseDto.builder()
                .amount(userTransaction.getAmount())
                .status(status)
                .userId(userTransaction.getUserId())
                .build();
    }

    public static TransactionResponseDto toDto(final TransactionRequestDto dto, final TransactionStatus status) {
        return TransactionResponseDto.builder()
                .amount(dto.getAmount())
                .status(status)
                .userId(dto.getUserId())
                .build();
    }
}

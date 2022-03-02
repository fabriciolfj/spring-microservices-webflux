package com.github.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponseDto {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}

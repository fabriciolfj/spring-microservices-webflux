package com.github.userservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Builder
@Data
@Table("user_transaction")
public class UserTransaction {

    @Id
    private Integer id;
    private Integer userId;
    private Integer amount;
    private LocalDateTime transactionDate;

}

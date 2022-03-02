package com.github.userservice.repository;

import com.github.userservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {

    Flux<UserTransaction> findByUserId(final int userId);
}

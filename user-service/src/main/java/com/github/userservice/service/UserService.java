package com.github.userservice.service;

import com.github.userservice.Util.EntityDtoUtil;
import com.github.userservice.dto.UserDto;
import com.github.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserDto> getAll() {
        return userRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserId(final int userId) {
        return this.userRepository.findById(userId)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> createUser(final Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(final int userId, final Mono<UserDto> userDtoMono) {
        return userRepository.findById(userId)
                .flatMap(u -> userDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(u.getId())))
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(final int userId) {
        return userRepository.deleteById(userId);
    }
}

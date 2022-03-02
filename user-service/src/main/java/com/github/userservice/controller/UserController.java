package com.github.userservice.controller;

import com.github.userservice.dto.UserDto;
import com.github.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserDto> all() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> getById(@PathVariable("id") final int id) {
        return userService.getUserId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDto> createUser(@RequestBody final Mono<UserDto> userDtoMono) {
        return userService.createUser(userDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable("id") final int id, @RequestBody final Mono<UserDto> userDtoMono) {
        return userService.updateUser(id, userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") final int id) {
        return userService.deleteUser(id);
    }
}

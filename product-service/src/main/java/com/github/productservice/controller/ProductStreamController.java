package com.github.productservice.controller;

import com.github.productservice.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/product")
@RestController
@RequiredArgsConstructor
public class ProductStreamController {

    private final Flux<ProductDTO> flux;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDTO> flux() {
        return flux;
    }
}

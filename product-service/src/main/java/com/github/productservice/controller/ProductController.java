package com.github.productservice.controller;

import com.github.productservice.dto.ProductDTO;
import com.github.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<ProductDTO> all() {
        return productService.getAll();
    }

    @GetMapping("/price-range")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<ProductDTO> getBetweenPrice(@RequestParam("min") int min, @RequestParam("max") int max) {
        return productService.getProductByPriceRange(min, max);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> findById(@PathVariable("id") final String id) {
        this.simulateRandomException();
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDTO> insertProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        return productService.insertProduct(productDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>>  updateProduct(@PathVariable("id") final String id, @RequestBody final Mono<ProductDTO> dtoMono) {
        return productService.updateProduct(dtoMono, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") final String id) {
        return productService.deleteProduct(id);
    }

    private void simulateRandomException() {
        final int value = ThreadLocalRandom.current().nextInt(1, 10);

        if (value > 5) {
            throw new RuntimeException("something is wrong");
        }
    }
}

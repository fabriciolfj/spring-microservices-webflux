package com.github.productservice.service;

import com.github.productservice.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {

    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        var products = Arrays.asList(
                new ProductDTO(null, "arroz", 10),
                new ProductDTO(null, "feijao", 23),
                new ProductDTO(null, "farinha", 34),
                new ProductDTO(null, "couve", 3),
                new ProductDTO(null, "copo", 62));

        Flux.fromStream(products.stream())
                .flatMap(p -> productService.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }
}

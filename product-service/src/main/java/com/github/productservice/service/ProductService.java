package com.github.productservice.service;

import com.github.productservice.dto.ProductDTO;
import com.github.productservice.repository.ProductRepository;
import com.github.productservice.util.EntityDTOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Flux<ProductDTO> getAll() {
        return this.repository.findAll()
                .map(EntityDTOUtil::toDto);
    }

    public Mono<ProductDTO> getProductById(final String id) {
        return this.repository.findById(id)
                .map(EntityDTOUtil::toDto);
    }

    public Mono<ProductDTO> insertProduct(final Mono<ProductDTO> dto) {
        return dto.map(EntityDTOUtil::toEntity)
                .flatMap(repository::save)
                .map(EntityDTOUtil::toDto);
    }

    public Mono<ProductDTO> updateProduct(final Mono<ProductDTO> dtoMono, final String id) {
        return repository.findById(id)
                .flatMap(p -> dtoMono.map(EntityDTOUtil::toEntity).doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(EntityDTOUtil::toDto);

    }

    public Mono<Void> deleteProduct(final String id) {
        return repository.deleteById(id);
    }
}
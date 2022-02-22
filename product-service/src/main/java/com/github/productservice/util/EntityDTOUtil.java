package com.github.productservice.util;

import com.github.productservice.dto.ProductDTO;
import com.github.productservice.entity.Product;

public class EntityDTOUtil {

    private EntityDTOUtil() { }

    public static ProductDTO toDto(final Product product) {
        return ProductDTO.builder()
                .description(product.getDescription())
                .id(product.getId())
                .price(product.getPrice())
                .build();
    }

    public static Product toEntity(final ProductDTO dto) {
        return Product.builder()
                .description(dto.getDescription())
                .id(dto.getId())
                .price(dto.getPrice())
                .build();
    }
}

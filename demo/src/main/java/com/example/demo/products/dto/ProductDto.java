package com.example.demo.products.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, String description,
                         @JsonFormat(shape = JsonFormat.Shape.STRING)
                         BigDecimal price,
                         String productType) {
}

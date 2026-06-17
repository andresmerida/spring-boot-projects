package com.example.demo.products.dto;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price, String productType) {
}

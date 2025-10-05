package com.am.spring_boot_redis_cache.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, BigDecimal price) {
}

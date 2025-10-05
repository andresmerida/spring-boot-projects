package com.am.spring_boot_redis_cache.dto.request;

import java.math.BigDecimal;

public record ProductRequest(String name, BigDecimal price) {
}

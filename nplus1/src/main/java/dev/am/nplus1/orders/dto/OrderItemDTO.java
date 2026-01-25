package dev.am.nplus1.orders.dto;

import java.math.BigDecimal;

public record OrderItemDTO(Long id, String code, String name, BigDecimal price, int quantity) {
}

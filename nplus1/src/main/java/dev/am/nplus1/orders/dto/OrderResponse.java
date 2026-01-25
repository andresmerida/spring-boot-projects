package dev.am.nplus1.orders.dto;

import java.util.List;

public record OrderResponse(Long id, String orderNumber, String username, CustomerRequest customer,
                            Address deliveryAddress, List<OrderItemDTO> items) {
}

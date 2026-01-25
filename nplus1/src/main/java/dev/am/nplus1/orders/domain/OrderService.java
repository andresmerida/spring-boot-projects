package dev.am.nplus1.orders.domain;

import dev.am.nplus1.orders.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> findAll(boolean detailed);
}

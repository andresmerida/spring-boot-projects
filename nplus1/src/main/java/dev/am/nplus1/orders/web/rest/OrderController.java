package dev.am.nplus1.orders.web.rest;

import dev.am.nplus1.orders.domain.OrderService;
import dev.am.nplus1.orders.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
class OrderController {
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<List<OrderResponse>> getAllOrders(@RequestParam(required = false, defaultValue = "false") boolean detailed) {
        return ResponseEntity
                .ok()
                .body(orderService.findAll(detailed));
    }
}

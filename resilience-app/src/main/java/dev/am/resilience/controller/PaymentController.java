package dev.am.resilience.controller;

import dev.am.resilience.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/pay")
    String processPayment(@RequestParam String orderId, @RequestParam double amount) {
        return paymentService.processPayment(orderId, amount);
    }
}

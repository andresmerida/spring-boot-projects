package dev.am.nplus1.orders.domain;

import dev.am.nplus1.orders.dto.CustomerRequest;
import dev.am.nplus1.orders.dto.OrderItemDTO;
import dev.am.nplus1.orders.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
class OrderMapper {

    OrderResponse toDTO(OrderEntity entity) {
        return new OrderResponse(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getUsername(),
                instanceCustomerRequest(entity),
                entity.getDeliveryAddress(),
                List.of()
        );
    }

    OrderResponse toDTOWithItems(OrderEntity entity) {
        return new OrderResponse(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getUsername(),
                instanceCustomerRequest(entity),
                entity.getDeliveryAddress(),
                getItemList(entity.getItems())
        );
    }

    private CustomerRequest instanceCustomerRequest(OrderEntity entity) {
        return new CustomerRequest(
                entity.getCustomerName(),
                entity.getCustomerEmail(),
                entity.getCustomerPhone()
        );
    }

    private List<OrderItemDTO> getItemList(Set<OrderItemEntity> items) {
        return items.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getCode(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity()))
                .toList();
    }
}

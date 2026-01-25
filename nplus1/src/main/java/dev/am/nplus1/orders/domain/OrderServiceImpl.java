package dev.am.nplus1.orders.domain;

import dev.am.nplus1.orders.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(boolean detailed) {
        if (detailed) {
            List<OrderEntity> orderEntities;
            if (Math.random() < 0.5) {
                orderEntities = orderRepository.findAllOrdersWithItems_usingJoinFetch();
                log.info("Using join fetch");
            } else {
                orderEntities = orderRepository.findAllOrdersWithItems_usingEntityGraph();
                log.info("Using entity graph");
            }

            return orderEntities.stream()
                    .map(orderMapper::toDTOWithItems)
                    .toList();
        }

        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
    }
}

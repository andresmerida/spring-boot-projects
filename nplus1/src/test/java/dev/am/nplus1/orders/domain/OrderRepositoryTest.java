package dev.am.nplus1.orders.domain;

import dev.am.nplus1.DBTestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBTestcontainersConfig.class)
@Sql("/test-data.sql")
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Test
    void findAllOrdersWithItems_usingJoinFetch() {
        List<OrderEntity> orders = orderRepository.findAllOrdersWithItems_usingJoinFetch();
        assertEquals(2, orders.size());
        assertEquals(4, orders.getFirst().getItems().size());
        assertEquals(2, orders.get(1).getItems().size());
    }

    @Test
    void findAllOrdersWithItems_usingEntityGraph() {
        List<OrderEntity> orders = orderRepository.findAllOrdersWithItems_usingEntityGraph();
        assertEquals(2, orders.size());
        assertEquals(4, orders.getFirst().getItems().size());
        assertEquals(2, orders.get(1).getItems().size());
    }
}
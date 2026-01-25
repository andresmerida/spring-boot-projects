package dev.am.nplus1.orders.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select o from OrderEntity o join fetch o.items")
    List<OrderEntity> findAllOrdersWithItems_usingJoinFetch();

    @EntityGraph(attributePaths = "items")
    @Query("select o from OrderEntity o")
    List<OrderEntity> findAllOrdersWithItems_usingEntityGraph();
}

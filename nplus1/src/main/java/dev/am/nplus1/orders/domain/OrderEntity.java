package dev.am.nplus1.orders.domain;

import dev.am.nplus1.orders.domain.enums.OrderStatus;
import dev.am.nplus1.orders.dto.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_generator")
    @SequenceGenerator(name = "order_seq_generator", sequenceName = "order_id_seq")
    private Long id;

    @Column(nullable = false, name = "order_number")
    private String orderNumber;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "customer_name")
    private String customerName;

    @Column(nullable = false, name = "customer_email")
    private String customerEmail;

    @Column(nullable = false, name = "customer_phone")
    private String customerPhone;

    @Embedded
    @AttributeOverrides(
        value = {
                @AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1", nullable = false)),
                @AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
                @AttributeOverride(name = "city", column = @Column(name = "delivery_address_city", nullable = false)),
                @AttributeOverride(name = "state", column = @Column(name = "delivery_address_state", nullable = false)),
                @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code", nullable = false)),
                @AttributeOverride(name = "country", column = @Column(name = "delivery_address_country", nullable = false)),
        })
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private String comments;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItemEntity> items;

    OrderEntity() {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderNumber);
    }
}

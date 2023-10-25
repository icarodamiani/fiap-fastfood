package io.fiap.fastfood.driven.core.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public record Order(
    Long id,
    Customer customerId,
    List<OrderItem> orderItemList,
    Date createdAt,
    Long number) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Customer> getCustomer() {
        return Optional.ofNullable(customerId());
    }

    public static final class OrderBuilder {
        private Long id;
        private Customer customerId;
        private List<OrderItem> orderItemList;
        private Date createdAt;
        private Long number;

        private OrderBuilder() {
        }

        public static OrderBuilder builder() {
            return new OrderBuilder();
        }

        public OrderBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withCustomer(Customer customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderBuilder withOrderItem(List<OrderItem> orderItemList) {
            this.orderItemList = orderItemList;
            return this;
        }

        public OrderBuilder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderBuilder withNumber(Long number) {
            this.number = number;
            return this;
        }

        public Order build() {
            return new Order(id, customerId, orderItemList, createdAt, number);
        }
    }
}

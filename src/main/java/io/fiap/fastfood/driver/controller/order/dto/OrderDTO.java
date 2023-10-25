package io.fiap.fastfood.driver.controller.order.dto;

import io.fiap.fastfood.driver.controller.customer.dto.CustomerDTO;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public record OrderDTO(Long id,
                       @NotNull CustomerDTO customerId,
                       @NotNull List<OrderItemDTO> orderItemList,
                       @NotNull Date createdAt,
                       @NotNull Long number) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<CustomerDTO> getCustomer() {
        return Optional.ofNullable(customerId());
    }

    Optional<List<OrderItemDTO>> getOrderItemList() {
        return Optional.ofNullable(orderItemList());
    }

    public static final class OrderDTOBuilder {
        private Long id;
        private @NotNull CustomerDTO customerId;
        private @NotNull List<OrderItemDTO> orderItemList;
        private @NotNull Date createdAt;
        private @NotNull Long number;

        private OrderDTOBuilder() {
        }

        public static OrderDTOBuilder builder() {
            return new OrderDTOBuilder();
        }

        public OrderDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderDTOBuilder withCustomer(CustomerDTO customerId) {
            this.customerId = customerId;
            return this;
        }

        public OrderDTOBuilder withOrderItemList(List<OrderItemDTO> orderItemList) {
            this.orderItemList = orderItemList;
            return this;
        }

        public OrderDTOBuilder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderDTOBuilder withNumber(Long number) {
            this.number = number;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(id, customerId, orderItemList, createdAt, number);
        }
    }
}
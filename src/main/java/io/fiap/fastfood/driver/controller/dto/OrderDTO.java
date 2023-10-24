package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record OrderDTO(Long id,
                         @NotNull CustomerDTO customerId,
                         @NotNull List<OrderItemDTO> orderItemList,
                         @NotNull PaymentDTO paymentId,
                         @NotNull SalesPointDTO salesPointId,
                         @NotNull Date createdAt,
                         @NotNull Long number,
                         @NotNull OrderStatusDTO orderStatusId) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<CustomerDTO> getCustomer() {
        return Optional.ofNullable(customerId());
    }

    Optional<List<OrderItemDTO>> getOrderItemList() {
        return Optional.ofNullable(orderItemList());
    }

    Optional<PaymentDTO> getPayment() {
        return Optional.ofNullable(paymentId());
    }

    Optional<SalesPointDTO> getSalesPoint() {
        return Optional.ofNullable(salesPointId());
    }

    public static final class OrderDTOBuilder {
        private Long id;
        private @NotNull CustomerDTO customerId;
        private @NotNull List<OrderItemDTO> orderItemList;
        private @NotNull PaymentDTO paymentId;
        private @NotNull SalesPointDTO salesPointId;
        private @NotNull Date createdAt;
        private @NotNull Long number;
        private @NotNull OrderStatusDTO orderStatusId;

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

        public OrderDTOBuilder withPayment(PaymentDTO paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public OrderDTOBuilder withSalesPoint(SalesPointDTO salesPointId) {
            this.salesPointId = salesPointId;
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

        public OrderDTOBuilder withOrderStatus(OrderStatusDTO orderStatusId) {
            this.orderStatusId = orderStatusId;
            return this;
        }

        public OrderDTO build() {
            return new OrderDTO(id, customerId, orderItemList, paymentId, salesPointId, createdAt, number, orderStatusId);
        }
    }
}

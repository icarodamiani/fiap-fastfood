package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record OrderStatusDTO(Long id,
                         @NotNull String name,
                         @NotNull String description,
                         @NotNull Boolean customerFriendly) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getName() {
        return Optional.ofNullable(name());
    }

    Optional<String> getDescription() {
        return Optional.ofNullable(description());
    }

    Optional<Boolean> getCustomerFriendly() {
        return Optional.ofNullable(customerFriendly());
    }

    public static final class OrderStatusDTOBuilder {
        private Long id;
        private @NotNull String name;
        private @NotNull String description;
        private @NotNull Boolean customerFriendly;

        private OrderStatusDTOBuilder() {
        }

        public static OrderStatusDTOBuilder builder() {
            return new OrderStatusDTOBuilder();
        }

        public OrderStatusDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderStatusDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public OrderStatusDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public OrderStatusDTOBuilder withCustomerFriendly(Boolean customerFriendly) {
            this.customerFriendly = customerFriendly;
            return this;
        }

        public OrderStatusDTO build() {
            return new OrderStatusDTO(id, name, description, customerFriendly);
        }
    }
}

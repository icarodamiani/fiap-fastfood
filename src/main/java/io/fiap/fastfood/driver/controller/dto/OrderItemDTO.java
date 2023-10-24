package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record OrderItemDTO(Long id,
                         @NotNull Long productId,
                         @NotNull Long amount,
                         @NotNull String quote) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Long> getProductId() {
        return Optional.ofNullable(productId());
    }

    Optional<Long> getAmount() {
        return Optional.ofNullable(amount());
    }

    Optional<String> getQuote() {
        return Optional.ofNullable(quote());
    }

    public static final class OrderItemDTOBuilder {
        private Long id;
        private @NotNull Long productId;
        private @NotNull Long amount;
        private @NotNull String quote;

        private OrderItemDTOBuilder() {
        }

        public static OrderItemDTOBuilder builder() {
            return new OrderItemDTOBuilder();
        }

        public OrderItemDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderItemDTOBuilder withProductId(Long productId) {
            this.productId = productId;
            return this;
        }

        public OrderItemDTOBuilder withAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public OrderItemDTOBuilder withQuote(String quote) {
            this.quote = quote;
            return this;
        }

        public OrderItemDTO build() {
            return new OrderItemDTO(id, productId, amount, quote);
        }
    }
}

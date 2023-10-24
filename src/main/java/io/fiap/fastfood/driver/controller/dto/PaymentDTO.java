package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record PaymentDTO(Long id,
                         @NotNull String method,
                         @NotNull BigDecimal amount,
                         @NotNull Date date,
                         @NotNull ProofDTO proofId) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getMethod() {
        return Optional.ofNullable(method());
    }

    Optional<BigDecimal> getAmount() {
        return Optional.ofNullable(amount());
    }

    Optional<Date> getDate() {
        return Optional.ofNullable(date());
    }

    Optional<ProofDTO> getProofId() {
        return Optional.ofNullable(proofId());
    }

    public static final class PaymentDTOBuilder {
        private Long id;
        private @NotNull String method;
        private @NotNull BigDecimal amount;
        private @NotNull Date date;
        private @NotNull ProofDTO proofId;

        private PaymentDTOBuilder() {
        }

        public static PaymentDTOBuilder builder() {
            return new PaymentDTOBuilder();
        }

        public PaymentDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PaymentDTOBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public PaymentDTOBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentDTOBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public PaymentDTOBuilder withProofId(ProofDTO proofId) {
            this.proofId = proofId;
            return this;
        }

        public PaymentDTO build() {
            return new PaymentDTO(id, method, amount, date, proofId);
        }
    }
}

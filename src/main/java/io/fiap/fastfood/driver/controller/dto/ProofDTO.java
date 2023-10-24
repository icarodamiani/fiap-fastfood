package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record ProofDTO(Long id,
                       @NotNull Date date,
                       @NotNull BigDecimal number) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<Date> getDate() {
        return Optional.ofNullable(date());
    }

    Optional<BigDecimal> getNumber() {
        return Optional.ofNullable(number());
    }

    public static final class ProofDTOBuilder {
        private Long id;
        private @NotNull Date date;
        private @NotNull BigDecimal number;

        private ProofDTOBuilder() {
        }

        public static ProofDTOBuilder builder() {
            return new ProofDTOBuilder();
        }

        public ProofDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProofDTOBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public ProofDTOBuilder withNumber(BigDecimal number) {
            this.number = number;
            return this;
        }

        public ProofDTO build() {
            return new ProofDTO(id, date, number);
        }
    }
}

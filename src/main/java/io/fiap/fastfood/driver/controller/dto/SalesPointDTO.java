package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

import io.fiap.fastfood.driven.core.exception.domain.model.SalesPoint;
import reactor.util.annotation.Nullable;

public record SalesPointDTO(Long id,
                         @NotNull String description) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getDescription() {
        return Optional.ofNullable(description());
    }

    public static final class SalesPointDTOBuilder {
        private Long id;
        private @NotNull String description;

        private SalesPointDTOBuilder() {
        }

        public static SalesPointDTOBuilder builder() {
            return new SalesPointDTOBuilder();
        }

        public SalesPointDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SalesPointDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SalesPointDTO build() {
            return new SalesPointDTO(id, description);
        }
    }
}

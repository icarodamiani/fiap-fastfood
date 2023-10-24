package io.fiap.fastfood.driven.core.exception.domain.model;

import java.util.Date;
import java.util.Optional;

public record SalesPoint(
    Long id,
    String description) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getDescription() {
        return Optional.ofNullable(description());
    }

    public static final class SalesPointBuilder {
        private Long id;
        private String description;

        private SalesPointBuilder() {
        }

        public static SalesPointBuilder builder() {
            return new SalesPointBuilder();
        }

        public SalesPointBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SalesPointBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SalesPoint build() {
            return new SalesPoint(id, description);
        }
    }
}

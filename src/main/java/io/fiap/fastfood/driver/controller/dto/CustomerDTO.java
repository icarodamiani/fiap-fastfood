package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import io.fiap.fastfood.driven.core.exception.domain.model.SalesPoint;
import reactor.util.annotation.Nullable;

public record CustomerDTO(Long id,
                         @NotNull String name,
                         @NotNull String identity,
                         @NotNull String email,
                         @NotNull String number) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<String> getName() {
        return Optional.ofNullable(name());
    }

    Optional<String> getIdentity() {
        return Optional.ofNullable(identity());
    }

    Optional<String> getEmail() {
        return Optional.ofNullable(email());
    }

    Optional<String> getNumber() {
        return Optional.ofNullable(number());
    }

    public static final class CustomerDTOBuilder {
        private Long id;
        private @NotNull String name;
        private @NotNull String identity;
        private @NotNull String email;
        private @NotNull String number;

        private CustomerDTOBuilder() {
        }

        public static CustomerDTOBuilder builder() {
            return new CustomerDTOBuilder();
        }

        public CustomerDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CustomerDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CustomerDTOBuilder withIdentity(String identity) {
            this.identity = identity;
            return this;
        }

        public CustomerDTOBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerDTOBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(id, name, identity, email, number);
        }
    }
}

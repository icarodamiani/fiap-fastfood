package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;

public record ProductTypeDTO(String id,
                             @NotNull @Size(min = 2, max = 50) String description) {
    Optional<String> getId() {
        return Optional.ofNullable(id());
    }
}

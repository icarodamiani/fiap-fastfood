package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record ProductDTO(String id,
                         @NotNull String typeId,
                         @NotNull @Size(min = 2, max = 50) String description,
                         @NotNull @Min(0) BigDecimal price,
                         @NotNull @Min(0) String availability,
                         @Nullable ProductTypeDTO type) {

    Optional<String> getId() {
        return Optional.ofNullable(id());
    }

    Optional<ProductTypeDTO> getType() {
        return Optional.ofNullable(type());
    }
}

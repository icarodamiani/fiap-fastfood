package io.fiap.fastfood.driver.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record SalesPointDTO(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        String id,
        String description,
        List<TillDTO> tills
) {
    @Override
    public String description() {
        return description;
    }

    @Override
    public List<TillDTO> tills() {
        return tills;
    }

    @Override
    public String id() {
        return id;
    }
}

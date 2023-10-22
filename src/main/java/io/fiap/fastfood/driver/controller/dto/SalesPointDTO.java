package io.fiap.fastfood.driver.controller.dto;

import java.util.List;

public record SalesPointDTO(
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
}

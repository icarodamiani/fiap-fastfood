package io.fiap.fastfood.driver.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;
import reactor.util.annotation.Nullable;

public record ProductDTO(Long id,
                         @NotNull Long typeId,
                         @NotNull @Size(min = 2, max = 50) String description,
                         @NotNull @Min(0) BigDecimal price,
                         @NotNull @Min(0) Integer availability,
                         @Nullable ProductTypeDTO type) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<ProductTypeDTO> getType() {
        return Optional.ofNullable(type());
    }


    public static final class ProductDTOBuilder {
        private Long id;
        private @NotNull Long typeId;
        private @NotNull @Size(min = 2, max = 50) String description;
        private @NotNull @Min(0) BigDecimal price;
        private @NotNull @Min(0) Integer availability;
        private @Nullable ProductTypeDTO type;

        private ProductDTOBuilder() {
        }

        public static ProductDTOBuilder builder() {
            return new ProductDTOBuilder();
        }

        public ProductDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductDTOBuilder withTypeId(Long typeId) {
            this.typeId = typeId;
            return this;
        }

        public ProductDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductDTOBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDTOBuilder withAvailability(Integer availability) {
            this.availability = availability;
            return this;
        }

        public ProductDTOBuilder withType(ProductTypeDTO type) {
            this.type = type;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(id, typeId, description, price, availability, type);
        }
    }
}

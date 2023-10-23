package io.fiap.fastfood.driven.core.domain.model;

import java.math.BigDecimal;
import java.util.Optional;

public record Product(
    Long id,
    Long typeId,
    String description,
    BigDecimal price,
    Integer availability,
    ProductType type) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<ProductType> getType() {
        return Optional.ofNullable(type());
    }


    public static final class ProductBuilder {
        private Long id;
        private Long typeId;
        private String description;
        private BigDecimal price;
        private Integer availability;
        private ProductType type;

        private ProductBuilder() {
        }

        public static ProductBuilder builder() {
            return new ProductBuilder();
        }

        public ProductBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withTypeId(Long typeId) {
            this.typeId = typeId;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withAvailability(Integer availability) {
            this.availability = availability;
            return this;
        }

        public ProductBuilder withType(ProductType type) {
            this.type = type;
            return this;
        }

        public Product build() {
            return new Product(id, typeId, description, price, availability, type);
        }
    }
}

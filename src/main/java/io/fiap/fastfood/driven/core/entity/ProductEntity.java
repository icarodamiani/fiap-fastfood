package io.fiap.fastfood.driven.core.entity;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("produto")
public record ProductEntity(
    @Id
    @Field("id_produto")
    Long id,
    @Field("id_categoria")
    Long typeId,
    @Field("descricao")
    String description,
    @Field("preco")
    BigDecimal price,
    @Field("quantidade")
    Integer availability,
    @Field("categoria")
    ProductTypeEntity type) {


    public static final class ProductEntityBuilder {
        private Long id;
        private Long typeId;
        private String description;
        private BigDecimal price;
        private Integer availability;
        private ProductTypeEntity type;

        private ProductEntityBuilder() {
        }

        public static ProductEntityBuilder builder() {
            return new ProductEntityBuilder();
        }

        public ProductEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductEntityBuilder withTypeId(Long typeId) {
            this.typeId = typeId;
            return this;
        }

        public ProductEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductEntityBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductEntityBuilder withAvailability(Integer availability) {
            this.availability = availability;
            return this;
        }

        public ProductEntityBuilder withType(ProductTypeEntity type) {
            this.type = type;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(id, typeId, description, price, availability, type);
        }
    }
}

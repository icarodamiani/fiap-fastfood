package io.fiap.fastfood.driven.core.entity;

import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("produto")
public class ProductEntity {
    @Id
    @Column("id_produto")
    private Long id;

    @Column("id_categoria")
    private Long typeId;

    @Column("descricao")
    private String description;

    @Column("price")
    private BigDecimal price;

    @Column("quantidade")
    private Integer availability;

    @Transient
    private ProductTypeEntity type;

    public ProductEntity() {
        //default
    }

    public ProductEntity(Long id, Long typeId, String description, BigDecimal price, Integer availability) {
        this.id = id;
        this.typeId = typeId;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public ProductEntity(Long typeId, String description, BigDecimal price, Integer availability) {
        this.typeId = typeId;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public ProductTypeEntity getType() {
        return type;
    }

    public void setType(ProductTypeEntity type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity product = (ProductEntity) o;
        return Objects.equals(id, product.id) && Objects.equals(typeId, product.typeId) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(availability, product.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, description, price, availability);
    }


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
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(id);
            productEntity.setTypeId(typeId);
            productEntity.setDescription(description);
            productEntity.setPrice(price);
            productEntity.setAvailability(availability);
            productEntity.setType(type);
            return productEntity;
        }
    }
}

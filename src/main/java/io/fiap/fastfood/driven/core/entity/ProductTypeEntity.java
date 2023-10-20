package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("produto")
public class ProductTypeEntity {
    @Id
    @Column("id_categoria")
    private Long id;

    @Column("descricao")
    private String description;

    public ProductTypeEntity() {
    }

    public ProductTypeEntity(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public ProductTypeEntity(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeEntity that = (ProductTypeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }


    public static final class ProductTypeEntityBuilder {
        private Long id;
        private String description;

        private ProductTypeEntityBuilder() {
        }

        public static ProductTypeEntityBuilder builder() {
            return new ProductTypeEntityBuilder();
        }

        public ProductTypeEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductTypeEntityBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductTypeEntity build() {
            ProductTypeEntity productTypeEntity = new ProductTypeEntity();
            productTypeEntity.setId(id);
            productTypeEntity.setDescription(description);
            return productTypeEntity;
        }
    }
}

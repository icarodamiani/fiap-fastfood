package io.fiap.fastfood.core.entity;

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
    private String typeId;

    @Column("descricao")
    private String description;

    @Column("price")
    private BigDecimal price;

    @Column("quantidade")
    private String availability;

    @Transient
    private ProductTypeEntity type;

    public ProductEntity() {
        //default
    }

    public ProductEntity(Long id, String typeId, String description, BigDecimal price, String availability) {
        this.id = id;
        this.typeId = typeId;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public ProductEntity(String typeId, String description, BigDecimal price, String availability) {
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
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
}

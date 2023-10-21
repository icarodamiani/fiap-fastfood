package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ponto_venda")
public class SalesPointEntity {
    @Id
    @Column("id_ponto_venda")
    private Long id;

    @Column("descricao")
    private String description;

    public SalesPointEntity() {
        //default
    }

    public SalesPointEntity(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public SalesPointEntity(String description) {
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
        SalesPointEntity that = (SalesPointEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}

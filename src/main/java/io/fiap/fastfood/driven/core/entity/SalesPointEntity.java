package io.fiap.fastfood.driven.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Objects;


@Document("ponto_venda")
public class SalesPointEntity {
    @Id
    private String id;

    @Field("descricao")
    private String description;

    @Field("caixas")
    private List<TillEntity> tills;

    public SalesPointEntity() {
        //default
    }

    public SalesPointEntity(String id, String description, List<TillEntity> tills) {
        this.id = id;
        this.description = description;
        this.tills = tills;
    }

    public SalesPointEntity(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TillEntity> getTills() {
        return tills;
    }

    public void setTills(List<TillEntity> tills) {
        this.tills = tills;
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

package io.fiap.fastfood.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("caixa")
public class TillEntity { // CashDesk? BillingDay?
    @Id
    @Column("id_caixa")
    private Long id;

    @Column("data_hora_abertura")
    private String openAt;

    @Column("data_hora_fechamento")
    private String closedAt;

    @Column("id_ponto_venda")
    private String salesPointId;

    public TillEntity() {
    }

    public TillEntity(Long id, String openAt, String closedAt, String salesPointId) {
        this.id = id;
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.salesPointId = salesPointId;
    }

    public TillEntity(String openAt, String closedAt, String salesPointId) {
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.salesPointId = salesPointId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getSalesPointId() {
        return salesPointId;
    }

    public void setSalesPointId(String salesPointId) {
        this.salesPointId = salesPointId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TillEntity till = (TillEntity) o;
        return Objects.equals(id, till.id) && Objects.equals(openAt, till.openAt) && Objects.equals(closedAt, till.closedAt) && Objects.equals(salesPointId, till.salesPointId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openAt, closedAt, salesPointId);
    }
}

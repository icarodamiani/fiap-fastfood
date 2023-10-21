package io.fiap.fastfood.driven.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

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
    private Long idSalesPoint;


    public TillEntity() {
    }

    public TillEntity(Long id, String openAt, String closedAt, Long idSalesPoint) {
        this.id = id;
        this.openAt = openAt;
        this.closedAt = closedAt;
        this.idSalesPoint = idSalesPoint;
    }

    public TillEntity(String openAt, String closedAt) {
        this.openAt = openAt;
        this.closedAt = closedAt;
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

    public Long getIdSalesPoint() {
        return idSalesPoint;
    }

    public void setIdSalesPoint(Long idSalesPoint) {
        this.idSalesPoint = idSalesPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TillEntity that = (TillEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(openAt, that.openAt)
                && Objects.equals(closedAt, that.closedAt) && Objects.equals(idSalesPoint, that.idSalesPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openAt, closedAt, idSalesPoint);
    }
}

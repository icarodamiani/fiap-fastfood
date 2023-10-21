package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("pedido")
public class PaymentStatusEntity {
    @Id
    @Column("id_status_pagamento")
    private Long id;

    @Column("id_pagamento")
    private String paymentId;

    @Column("descricao")
    private String description;

    public PaymentStatusEntity() {
        //default
    }

    public PaymentStatusEntity(Long id, String paymentId, String description) {
        this.id = id;
        this.paymentId = paymentId;
        this.description = description;
    }

    public PaymentStatusEntity(String paymentId, String description) {
        this.paymentId = paymentId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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
        PaymentStatusEntity that = (PaymentStatusEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(paymentId, that.paymentId) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, description);
    }
}

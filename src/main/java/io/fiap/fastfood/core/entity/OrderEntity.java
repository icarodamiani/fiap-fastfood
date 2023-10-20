package io.fiap.fastfood.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("pedido")
public class OrderEntity {
    @Id
    @Column("id_pedido")
    private Long id;
    @Column("id_cliente")
    private String customerId;

    @Column("id_pagamento")
    private String paymentId; // accountId?
    @Column("id_ponto_venda")
    private String salesPointId;
    @Column("data_hora")
    private String createdAt;
    @Column("numero_pedido")
    private String number;

    public OrderEntity() {
        //default
    }

    public OrderEntity(String customerId, String salesPointId, String createdAt, String number) {
        this.customerId = customerId;
        this.salesPointId = salesPointId;
        this.createdAt = createdAt;
        this.number = number;
    }

    public OrderEntity(Long id, String customerId, String salesPointId, String createdAt, String number) {
        this.id = id;
        this.customerId = customerId;
        this.salesPointId = salesPointId;
        this.createdAt = createdAt;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSalesPointId() {
        return salesPointId;
    }

    public void setSalesPointId(String salesPointId) {
        this.salesPointId = salesPointId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(id, order.id) && Objects.equals(customerId, order.customerId)
            && Objects.equals(salesPointId, order.salesPointId) && Objects.equals(createdAt, order.createdAt)
            && Objects.equals(number, order.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, salesPointId, createdAt, number);
    }
}

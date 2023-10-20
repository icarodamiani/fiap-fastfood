package io.fiap.fastfood.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("comprovante")
public class ProofEntity {
    @Id
    @Column("id_comprovante")
    private Long id;

    @Column("id_pedido")
    private String orderId; //Não seria um comprovante de pagamento? Acho que o vínculo está errado.

    @Column("data_hora_emissao")
    private String emittedAt;

    @Column("numero")
    private String number;

    public ProofEntity() {
    }

    public ProofEntity(Long id, String orderId, String emittedAt, String number) {
        this.id = id;
        this.orderId = orderId;
        this.emittedAt = emittedAt;
        this.number = number;
    }

    public ProofEntity(String orderId, String emittedAt, String number) {
        this.orderId = orderId;
        this.emittedAt = emittedAt;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmittedAt() {
        return emittedAt;
    }

    public void setEmittedAt(String emittedAt) {
        this.emittedAt = emittedAt;
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
        ProofEntity proof = (ProofEntity) o;
        return Objects.equals(id, proof.id) && Objects.equals(orderId, proof.orderId) && Objects.equals(emittedAt, proof.emittedAt) && Objects.equals(number, proof.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, emittedAt, number);
    }
}

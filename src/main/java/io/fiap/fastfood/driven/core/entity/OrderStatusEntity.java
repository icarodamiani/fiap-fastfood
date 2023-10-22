package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("status_pedido")
public class OrderStatusEntity {
    @Id
    @Field("id_status")
    private Long id;

    @Field("nome")
    private String name;

    @Field("descricao")
    private String description; // accountId?

    @Field("habilitado_cliente")
    private Boolean customerFriendly;

    public OrderStatusEntity() {
        //default
    }

    public OrderStatusEntity(Long id, String name, String description, Boolean customerFriendly) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.customerFriendly = customerFriendly;
    }

    public OrderStatusEntity(String name, String description, Boolean customerFriendly) {
        this.name = name;
        this.description = description;
        this.customerFriendly = customerFriendly;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCustomerFriendly() {
        return customerFriendly;
    }

    public void setCustomerFriendly(Boolean customerFriendly) {
        this.customerFriendly = customerFriendly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatusEntity that = (OrderStatusEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(customerFriendly, that.customerFriendly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, customerFriendly);
    }
}

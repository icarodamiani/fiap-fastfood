package io.fiap.fastfood.driven.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document("pedido_acompanhamento")
public record OrderTrackingEntity(
        @Id
        String id,
        @Field("id_pedido")
        String orderId,

        @Field("numero_pedido")
        String orderNumber,

        @Field("status")
        String orderStatus,

        @Field("visibilidade")
        String role,

        @Field("data_hora")
        LocalDateTime orderDateTime
) {

    @Override
    public String id() {
        return id;
    }

    @Override
    public String orderId() {
        return orderId;
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public String orderStatus() {
        return orderStatus;
    }

    @Override
    public String role() {
        return role;
    }

    @Override
    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }
}

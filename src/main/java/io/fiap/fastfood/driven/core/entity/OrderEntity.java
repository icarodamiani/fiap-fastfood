package io.fiap.fastfood.driven.core.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.fiap.fastfood.driven.core.exception.domain.model.OrderStatus;

@Document("pedido")
public record OrderEntity(
    @Id
    @Field("id_pedido")
    Long id,
    @Field("id_cliente")
    CustomerEntity customerId,
    @Field("lista_item_pedido")
    List<OrderItemEntity> orderItemList,
    @Field("id_pagamento")
    PaymentEntity paymentId,
    @Field("id_ponto_venda")
    SalesPointEntity salesPointId,
    @Field("data_hora")
    Date createdAt,
    @Field("numero_pedido")
    Long number,
    @Field("id_status_pedido")
    OrderStatusEntity orderStatusId) {

        public static final class OrderEntityBuilder {
            private Long id;
            private CustomerEntity customerId;
            private List<OrderItemEntity> orderItemList;
            private PaymentEntity paymentId;
            private SalesPointEntity salesPointId;
            private Date createdAt;
            private Long number;
            private OrderStatusEntity orderStatusId;
    
            private OrderEntityBuilder() {
            }
    
            public static OrderEntityBuilder builder() {
                return new OrderEntityBuilder();
            }
    
            public OrderEntityBuilder withId(Long id) {
                this.id = id;
                return this;
            }
    
            public OrderEntityBuilder withCustomerId(CustomerEntity customerId) {
                this.customerId = customerId;
                return this;
            }

            public OrderEntityBuilder withOrderItemList(List<OrderItemEntity> orderItemList) {
                this.orderItemList = orderItemList;
                return this;
            }

            public OrderEntityBuilder withPaymentId(PaymentEntity paymentId) {
                this.paymentId = paymentId;
                return this;
            }
    
            public OrderEntityBuilder withSalesPointId(SalesPointEntity salesPointId) {
                this.salesPointId = salesPointId;
                return this;
            }
    
            public OrderEntityBuilder withCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
                return this;
            }

            public OrderEntityBuilder withNumber(Long number) {
                this.number = number;
                return this;
            }

            public OrderEntityBuilder withOrderStatusId(OrderStatusEntity orderStatusId) {
                this.orderStatusId = orderStatusId;
                return this;
            }
       
            public OrderEntity build() {
                return new OrderEntity(id, customerId, orderItemList, paymentId, salesPointId, createdAt, number, orderStatusId);
            }
        }

}

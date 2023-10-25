package io.fiap.fastfood.driven.core.entity;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("pedido")
public record OrderEntity(
    @Id
    @Field("id_pedido")
    Long id,
    @Field("id_cliente")
    CustomerEntity customerId,
    @Field("lista_item_pedido")
    List<OrderItemEntity> orderItemList,
    @Field("data_hora")
    Date createdAt,
    @Field("numero_pedido")
    Long number) {

        public static final class OrderEntityBuilder {
            private Long id;
            private CustomerEntity customerId;
            private List<OrderItemEntity> orderItemList;
            private Date createdAt;
            private Long number;

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

            public OrderEntityBuilder withCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
                return this;
            }

            public OrderEntityBuilder withNumber(Long number) {
                this.number = number;
                return this;
            }
       
            public OrderEntity build() {
                return new OrderEntity(id, customerId, orderItemList, createdAt, number);
            }
        }

}

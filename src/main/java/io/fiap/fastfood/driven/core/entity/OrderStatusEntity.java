package io.fiap.fastfood.driven.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("status_pedido")
public record OrderStatusEntity(
    @Id
    @Field("id_status")
    Long id,
    @Field("nome")
    String name,
    @Field("descricao")
    String description,
    @Field("habilitado_cliente")
    Boolean customerFriendly) {

        public static final class OrderStatusEntityBuilder {
            private Long id;
            private String name;
            private String description;
            private Boolean customerFriendly;
    
            private OrderStatusEntityBuilder() {
            }
    
            public static OrderStatusEntityBuilder builder() {
                return new OrderStatusEntityBuilder();
            }
    
            public OrderStatusEntityBuilder withId(Long id) {
                this.id = id;
                return this;
            }
    
            public OrderStatusEntityBuilder withName(String name) {
                this.name = name;
                return this;
            }
    
            public OrderStatusEntityBuilder withDescription(String description) {
                this.description = description;
                return this;
            }

            public OrderStatusEntityBuilder withCustomerFriendly(Boolean customerFriendly) {
                this.customerFriendly = customerFriendly;
                return this;
            }
            
            public OrderStatusEntity build() {
                return new OrderStatusEntity(id, name, description, customerFriendly);
            }
        }

}

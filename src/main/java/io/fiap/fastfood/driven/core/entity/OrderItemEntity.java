package io.fiap.fastfood.driven.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("item_pedido")
public record OrderItemEntity(
    @Id
    @Field("id_item_pedido")
    Long id,

    @Field("id_produto")
    Long productId,

    @Field("quantidade")
    Long amount,

    @Field("observacao")
    String quote) {

        public static final class OrderItemEntityBuilder {
            private Long id;
            private Long productId;
            private Long amount;
            private String quote;
    
            private OrderItemEntityBuilder() {
            }
    
            public static OrderItemEntityBuilder builder() {
                return new OrderItemEntityBuilder();
            }
    
            public OrderItemEntityBuilder withId(Long id) {
                this.id = id;
                return this;
            }
    
            public OrderItemEntityBuilder withProductId(Long productId) {
                this.productId = productId;
                return this;
            }
    
            public OrderItemEntityBuilder withAmount(Long amount) {
                this.amount = amount;
                return this;
            }

            public OrderItemEntityBuilder withQuote(String quote) {
                this.quote = quote;
                return this;
            }
            
            public OrderItemEntity build() {
                return new OrderItemEntity(id, productId, amount, quote);
            }
        }

}

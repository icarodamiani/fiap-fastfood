package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("item_pedido")
public class OrderItemEntity {
    @Id
    @Field("id_item_pedido")
    private Long id;

    @Field("id_pedido")
    private String orderId;

    @Field("id_produto")
    private String productId;

    @Field("quantidade")
    private String amount;

    @Field("obeservacao")
    private String quote;

    public OrderItemEntity() {
        //default
    }

    public OrderItemEntity(Long id, String orderId, String productId, String amount, String quote) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.quote = quote;
    }

    public OrderItemEntity(String orderId, String productId, String amount, String quote) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
        this.quote = quote;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemEntity item = (OrderItemEntity) o;
        return Objects.equals(id, item.id) && Objects.equals(orderId, item.orderId) && Objects.equals(productId, item.productId) && Objects.equals(amount, item.amount) && Objects.equals(quote, item.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, amount, quote);
    }
}

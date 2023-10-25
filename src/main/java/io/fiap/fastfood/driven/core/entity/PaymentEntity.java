package io.fiap.fastfood.driven.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("pagamento")
public record PaymentEntity(
    @Id
    @Field("id_pagamento")
    Long id,
    @Field("meio_pagamento")
    String method,
    @Field("valor")
    BigDecimal amount,
    @Field("data_hora")
    Date date,
    @Field("id_pedido")
    Long orderId) {

    public static final class PaymentEntityBuilder {
        private Long id;
        private String method;
        private BigDecimal amount;
        private Date date;
        private Long orderId;

        private PaymentEntityBuilder() {
        }

        public static PaymentEntityBuilder builder() {
            return new PaymentEntityBuilder();
        }

        public PaymentEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PaymentEntityBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public PaymentEntityBuilder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentEntityBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public PaymentEntityBuilder withOrderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public PaymentEntity build() {
            return new PaymentEntity(id, method, amount, date, orderId);
        }
    }

}

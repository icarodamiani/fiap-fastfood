package io.fiap.fastfood.driven.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("status_pagamento")
public record PaymentStatusEntity(
    @Id
    @Field("id_status_pagamento")
    Long id,
    @Field("id_pagamento")
    Long paymentId,
    @Field("descricao")
    String description) {

        public static final class PaymentStatusEntityBuilder {
            private Long id;
            private Long paymentId;
            private String description;
    
            private PaymentStatusEntityBuilder() {
            }
    
            public static PaymentStatusEntityBuilder builder() {
                return new PaymentStatusEntityBuilder();
            }
    
            public PaymentStatusEntityBuilder withId(Long id) {
                this.id = id;
                return this;
            }
    
            public PaymentStatusEntityBuilder withPaymentId(Long paymentId) {
                this.paymentId = paymentId;
                return this;
            }
    
            public PaymentStatusEntityBuilder withDescription(String description) {
                this.description = description;
                return this;
            }
       
            public PaymentStatusEntity build() {
                return new PaymentStatusEntity(id, paymentId, description);
            }
        }

}

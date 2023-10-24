package io.fiap.fastfood.driven.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("comprovante")
public record ProofEntity(
    @Id
    @Field("id_comprovante")
    Long id,
    @Field("data_hora_emissao")
    Date date,
    @Field("numero")
    BigDecimal number) {

    public static final class ProofEntityBuilder {
        private Long id;
        private Date date;
        private BigDecimal number;

        private ProofEntityBuilder() {
        }

        public static ProofEntityBuilder builder() {
            return new ProofEntityBuilder();
        }

        public ProofEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProofEntityBuilder withDate(Date date) {
            this.date = date;
            return this;
        }

        public ProofEntityBuilder withNumber(BigDecimal number) {
            this.number = number;
            return this;
        }

        public ProofEntity build() {
            return new ProofEntity(id, date, number);
        }
    }
}

package io.fiap.fastfood.driven.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("cliente")
public record CustomerEntity(
    @Id
    @Field("id_cliente")
    Long id,
    @Field("nome")
    String name,
    @Field("documento")
    String identity,
    @Field("email")
    String email,
    @Field("telefone")
    String number) {

        public static final class CustomerEntityBuilder {
            private Long id;
            private String name;
            private String identity;
            private String email;
            private String number;
    
            private CustomerEntityBuilder() {
            }
    
            public static CustomerEntityBuilder builder() {
                return new CustomerEntityBuilder();
            }
    
            public CustomerEntityBuilder withId(Long id) {
                this.id = id;
                return this;
            }
    
            public CustomerEntityBuilder withName(String name) {
                this.name = name;
                return this;
            }
    
            public CustomerEntityBuilder withIdentity(String identity) {
                this.identity = identity;
                return this;
            }
    
            public CustomerEntityBuilder withEmail(String email) {
                this.email = email;
                return this;
            }
    
            public CustomerEntityBuilder withNumber(String number) {
                this.number = number;
                return this;
            }
       
            public CustomerEntity build() {
                return new CustomerEntity(id, name, identity, email, number);
            }
        }

}

package io.fiap.fastfood.driven.core.entity;

import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

public class CustomerEntity {
    @Id
    @Field("id_cliente")
    private Long id;

    @Field("nome")
    private String name;

    @Field("documento")
    private String identity;

    @Field("email")
    private String email;

    @Field("telefone")
    private String number;

    public CustomerEntity() {
        //default
    }

    public CustomerEntity(Long id, String name, String identity, String email, String number) {
        this.id = id;
        this.name = name;
        this.identity = identity;
        this.email = email;
        this.number = number;
    }

    public CustomerEntity(String name, String identity, String email, String number) {
        this.name = name;
        this.identity = identity;
        this.email = email;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity customer = (CustomerEntity) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name)
            && Objects.equals(identity, customer.identity) && Objects.equals(email, customer.email)
            && Objects.equals(number, customer.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identity, email, number);
    }
}


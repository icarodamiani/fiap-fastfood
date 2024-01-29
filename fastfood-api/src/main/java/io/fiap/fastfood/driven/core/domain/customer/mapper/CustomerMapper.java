package io.fiap.fastfood.driven.core.domain.customer.mapper;

import io.fiap.fastfood.driven.core.domain.model.Customer;
import io.fiap.fastfood.driven.core.entity.CustomerEntity;
import io.fiap.fastfood.driver.controller.customer.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IdentityMapper.class})
public interface CustomerMapper {
    Customer domainFromDto(CustomerDTO customerDTO);

    CustomerDTO dtoFromDomain(Customer customer);

    CustomerEntity entityFromDomain(Customer customer);

    Customer domainFromEntity(CustomerEntity customerEntity);
}

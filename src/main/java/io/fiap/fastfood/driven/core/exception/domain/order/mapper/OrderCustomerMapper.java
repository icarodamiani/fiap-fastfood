package io.fiap.fastfood.driven.core.exception.domain.order.mapper;

import io.fiap.fastfood.driven.core.entity.CustomerEntity;
import io.fiap.fastfood.driven.core.exception.domain.model.Customer;
import io.fiap.fastfood.driver.controller.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderCustomerMapper {
    Customer domainFromDto(CustomerDTO customerDTO);

    CustomerDTO dtoFromDomain(Customer customer);

    CustomerEntity entityFromDomain(Customer customer);

    Customer domainFromEntity(CustomerEntity customerEntity);
}

package io.fiap.fastfood.driven.core.exception.domain.order.mapper;

import io.fiap.fastfood.driven.core.entity.OrderStatusEntity;
import io.fiap.fastfood.driven.core.exception.domain.model.OrderStatus;
import io.fiap.fastfood.driver.controller.dto.OrderStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatus domainFromDto(OrderStatusDTO orderStatusDTO);

    OrderStatusDTO dtoFromDomain(OrderStatus orderStatus);

    OrderStatusEntity entityFromDomain(OrderStatus orderStatus);

    OrderStatus domainFromEntity(OrderStatusEntity orderStatusEntity);
}

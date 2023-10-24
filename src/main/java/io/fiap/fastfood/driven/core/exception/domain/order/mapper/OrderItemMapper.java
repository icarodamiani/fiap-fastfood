package io.fiap.fastfood.driven.core.exception.domain.order.mapper;

import io.fiap.fastfood.driven.core.entity.OrderItemEntity;
import io.fiap.fastfood.driven.core.exception.domain.model.OrderItem;
import io.fiap.fastfood.driver.controller.dto.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem domainFromDto(OrderItemDTO orderItemDTO);

    OrderItemDTO dtoFromDomain(OrderItem orderItem);

    OrderItemEntity entityFromDomain(OrderItem orderItem);

    OrderItem domainFromEntity(OrderItemEntity orderItemEntity);
}

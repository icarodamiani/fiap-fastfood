package io.fiap.fastfood.driven.core.exception.domain.order.mapper;

import io.fiap.fastfood.driven.core.exception.domain.model.Order;
import io.fiap.fastfood.driven.core.entity.OrderEntity;
import io.fiap.fastfood.driver.controller.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderCustomerMapper.class, OrderPaymentMapper.class, OrderStatusMapper.class})
public interface OrderMapper {
    Order domainFromDto(OrderDTO orderDTO);

    OrderDTO dtoFromDomain(Order order);

    OrderEntity entityFromDomain(Order order);

    Order domainFromEntity(OrderEntity orderEntity);
}

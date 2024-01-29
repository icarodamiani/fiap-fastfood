package io.fiap.fastfood.driven.core.domain.order.mapper;

import io.fiap.fastfood.driven.core.domain.customer.mapper.CustomerMapper;
import io.fiap.fastfood.driven.core.domain.model.Order;
import io.fiap.fastfood.driven.core.domain.payment.mapper.PaymentMapper;
import io.fiap.fastfood.driven.core.entity.OrderEntity;
import io.fiap.fastfood.driver.controller.order.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {
    Order domainFromDto(OrderDTO orderDTO);

    OrderDTO dtoFromDomain(Order order);

    OrderEntity entityFromDomain(Order order);

    Order domainFromEntity(OrderEntity orderEntity);
}

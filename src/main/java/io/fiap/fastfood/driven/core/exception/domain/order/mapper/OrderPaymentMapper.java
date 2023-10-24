package io.fiap.fastfood.driven.core.exception.domain.order.mapper;

import io.fiap.fastfood.driven.core.entity.PaymentEntity;
import io.fiap.fastfood.driven.core.exception.domain.model.Payment;
import io.fiap.fastfood.driver.controller.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderPaymentMapper {
    Payment domainFromDto(PaymentDTO paymentDTO);

    PaymentDTO dtoFromDomain(Payment payment);

    PaymentEntity entityFromDomain(Payment payment);

    Payment domainFromEntity(PaymentEntity paymentEntity);
}

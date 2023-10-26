package io.fiap.fastfood.driven.core.domain.payment.mapper;

import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.entity.PaymentEntity;
import io.fiap.fastfood.driver.controller.order.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment domainFromDto(PaymentDTO paymentDTO);

    PaymentDTO dtoFromDomain(Payment payment);

    PaymentEntity entityFromDomain(Payment payment);

    Payment domainFromEntity(PaymentEntity paymentEntity);
}
package io.fiap.fastfood.driven.core.domain.billing.mapper;

import io.fiap.fastfood.driven.core.domain.model.Billing;
import io.fiap.fastfood.driven.core.entity.BillingEntity;
import io.fiap.fastfood.driver.controller.billing.dto.BillingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingMapper {
    Billing domainFromDto(BillingDTO billingDTO);

    BillingDTO dtoFromDomain(Billing billing);

    BillingEntity entityFromDomain(Billing billing);

    Billing domainFromEntity(BillingEntity billingEntity);
}

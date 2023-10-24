package io.fiap.fastfood.driven.core.domain.customer.mapper;

import io.fiap.fastfood.driven.core.domain.model.Identity;
import io.fiap.fastfood.driven.core.entity.IdentityEntity;
import io.fiap.fastfood.driver.controller.customer.dto.IdentityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    Identity domainFromDto(IdentityDTO productTypeDTO);

    IdentityDTO dtoFromDomain(Identity productType);

    IdentityEntity entityFromDomain(Identity productType);

    Identity domainFromEntity(IdentityEntity productTypeEntity);
}

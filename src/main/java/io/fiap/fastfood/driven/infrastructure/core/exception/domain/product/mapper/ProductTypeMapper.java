package io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.mapper;

import io.fiap.fastfood.driven.infrastructure.core.exception.domain.model.ProductType;
import io.fiap.fastfood.driven.infrastructure.core.entity.ProductTypeEntity;
import io.fiap.fastfood.driver.controller.dto.ProductTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductType domainFromDto(ProductTypeDTO productTypeDTO);

    ProductTypeDTO dtoFromDomain(ProductType productType);

    ProductTypeEntity entityFromDomain(ProductType productType);

    ProductType domainFromEntity(ProductTypeEntity productTypeEntity);
}

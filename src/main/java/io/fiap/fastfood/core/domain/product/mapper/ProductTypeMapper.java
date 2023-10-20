package io.fiap.fastfood.core.domain.product.mapper;

import io.fiap.fastfood.core.domain.model.ProductType;
import io.fiap.fastfood.core.entity.ProductEntity;
import io.fiap.fastfood.core.entity.ProductTypeEntity;
import io.fiap.fastfood.driver.controller.dto.ProductTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductType domainFromDto(ProductTypeDTO productTypeDTO);

    ProductTypeDTO dtoFromDomain(ProductType productType);

    ProductTypeEntity entityFromDomain(ProductType productType);

    ProductType domainFromEntity(ProductTypeEntity productTypeEntity);
}

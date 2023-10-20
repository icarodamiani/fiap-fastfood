package io.fiap.fastfood.core.domain.product.mapper;

import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.entity.ProductEntity;
import io.fiap.fastfood.driver.controller.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductTypeMapper.class})
public interface ProductMapper {
    Product domainFromDto(ProductDTO productDTO);

    ProductDTO dtoFromDomain(Product product);

    ProductEntity entityFromDomain(Product product);

    Product domainFromEntity(ProductEntity productEntity);
}

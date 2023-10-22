package io.fiap.fastfood.driven.core.domain.salespoint.mapper;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driver.controller.dto.SalesPointDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TillMapper.class})
public interface SalesPointMapper {
    SalesPoint domainFromDto(SalesPointDTO salesPointDTO);

    SalesPointDTO dtoFromDomain(SalesPoint salesPoint);

    SalesPointEntity entityFromDomain(SalesPoint salesPoint);

    SalesPoint domainFromEntity(SalesPointEntity salesPointEntity);
}

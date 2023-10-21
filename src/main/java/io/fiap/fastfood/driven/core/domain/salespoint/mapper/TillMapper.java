package io.fiap.fastfood.driven.core.domain.salespoint.mapper;

import io.fiap.fastfood.driven.core.domain.model.Till;
import io.fiap.fastfood.driven.core.entity.TillEntity;
import io.fiap.fastfood.driver.controller.dto.TillDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TillMapper {
    Till domainFromDto(TillDTO tillDTO);

    TillDTO dtoFromDomain(Till till);

    TillEntity entityFromDomain(Till till);
}

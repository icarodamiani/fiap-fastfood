package io.fiap.fastfood.driven.core.domain.salespoint.mapper;


import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.model.Till;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driven.core.entity.TillEntity;
import io.fiap.fastfood.driver.controller.dto.SalesPointDTO;
import io.fiap.fastfood.driver.controller.dto.TillDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesPointMapper {

    private final TillMapper tillMapper;

    public SalesPointMapper(TillMapper tillMapper) {
        this.tillMapper = tillMapper;
    }

    public SalesPoint domainFromDto(SalesPointDTO salesPointDTO) {
        return new SalesPoint(null, salesPointDTO.description(), tillsDomainFromDto(salesPointDTO.tills()));
    }

    private List<Till> tillsDomainFromDto(List<TillDTO> tillDTOS) {
        return tillDTOS.stream()
                .map(tillMapper::domainFromDto)
                .collect(Collectors.toList());
    }

    public SalesPointDTO dtoFromDomain(SalesPoint salesPoint) {
        return new SalesPointDTO(salesPoint.description(), tillsDtoFromDomain(salesPoint.tills()));
    }

    private List<TillDTO> tillsDtoFromDomain(List<Till> tills) {
        return tills.stream()
                .map(tillMapper::dtoFromDomain)
                .collect(Collectors.toList());
    }

    public SalesPointEntity entityFromDomain(SalesPoint salesPoint) {
        return new SalesPointEntity(salesPoint.id(), salesPoint.description(), tillsEntitiesFromDomain(salesPoint.tills()));
    }

    private List<TillEntity> tillsEntitiesFromDomain(List<Till> tills) {
        return tills.stream()
                .map(tillMapper::entityFromDomain)
                .collect(Collectors.toList());
    }


    public SalesPoint domainFromEntity(SalesPointEntity salesPointEntity) {
        return new SalesPoint(salesPointEntity.getId(), salesPointEntity.getDescription(), tillsDomainFromEntity(salesPointEntity.getTills()));
    }

    private List<Till> tillsDomainFromEntity(List<TillEntity> tillEntities) {
        return tillEntities.stream()
                .map(tillMapper::domainFromEntity)
                .collect(Collectors.toList());
    }
}

package io.fiap.fastfood.driven.core.domain.salespoint.mapper;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.model.Till;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driven.core.entity.TillEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalesPointDomainMapper {
    public SalesPoint domainFromEntity(SalesPointEntity salesPointEntity, List<TillEntity> tillEntities) {
        return new SalesPoint(
                salesPointEntity.getId(),
                salesPointEntity.getDescription(),
                tillDomainListFromTillEntityList(tillEntities)
        );
    }


    private List<Till> tillDomainListFromTillEntityList(List<TillEntity> tillEntities) {
        return tillEntities.stream()
                .map(tillEntity -> new Till(
                        tillEntity.getId(),
                        tillEntity.getOpenAt(),
                        tillEntity.getClosedAt(),
                        tillEntity.getIdSalesPoint())
                )
                .collect(Collectors.toList());
    }
}

package io.fiap.fastfood.driven.core.domain.salespoint.mapper;

import io.fiap.fastfood.driven.core.domain.model.Till;
import io.fiap.fastfood.driven.core.entity.TillEntity;
import io.fiap.fastfood.driver.controller.dto.TillDTO;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TillMapper {
    Till domainFromDto(TillDTO tillDTO) {
        return new Till(
                ZonedDateTime.parse(tillDTO.openAt()),
                ZonedDateTime.parse(tillDTO.closedAt()));
    }

    TillDTO dtoFromDomain(Till till) {
        return new TillDTO(till.openAt().toString(), till.closedAt().toString());
    }

    TillEntity entityFromDomain(Till till) {
        return new TillEntity(till.openAt().toLocalDateTime(), till.closedAt().toLocalDateTime());
    }

    Till domainFromEntity(TillEntity tillEntity) {
        return new Till(
                tillEntity.getOpenAt().atZone(ZoneId.of("UTC").normalized()),
                tillEntity.getClosedAt().atZone(ZoneId.of("UTC").normalized())
        );
    }
}

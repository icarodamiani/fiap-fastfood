package io.fiap.fastfood.driven.core.domain.salespoint.mapper;

import io.fiap.fastfood.driven.core.domain.model.Till;
import io.fiap.fastfood.driven.core.entity.TillEntity;
import io.fiap.fastfood.driver.controller.salespoint.dto.TillDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TillMapper {

    @Mapping(source = "tillDTO", target = "openAt", qualifiedByName = "openAtDto")
    @Mapping(source = "tillDTO", target = "closedAt", qualifiedByName = "closedAtDto")
    Till domainFromDto(TillDTO tillDTO);

    @Named("openAtDto")
    default ZonedDateTime openAtDtoToDomain(TillDTO tillDTO) {
        return ZonedDateTime.parse(tillDTO.openAt());
    }

    @Named("closedAtDto")
    default ZonedDateTime closedAtDtoToDomain(TillDTO tillDTO) {
        return ZonedDateTime.parse(tillDTO.openAt());
    }


    @Mapping(source = "till", target = "openAt", qualifiedByName = "openAt")
    @Mapping(source = "till", target = "closedAt", qualifiedByName = "closedAt")
    TillDTO dtoFromDomain(Till till);

    @Named("openAt")
    default String openAtDtoToDomain(Till till) {
        return till.openAt().toString();
    }

    @Named("closedAt")
    default String closedAtDomainToDto(Till till) {
        return till.closedAt().toString();
    }

    @Mapping(source = "till", target = "openAt", qualifiedByName = "openAtEntity")
    @Mapping(source = "till", target = "closedAt", qualifiedByName = "closedAtEntity")
    TillEntity entityFromDomain(Till till);

    @Named("openAtEntity")
    default LocalDateTime openAtDomainToEntity(Till till) {
        return till.openAt().toLocalDateTime();
    }

    @Named("closedAtEntity")
    default LocalDateTime closedAtDomainToEntity(Till till) {
        return till.closedAt().toLocalDateTime();
    }

    @Mapping(source = "tillEntity", target = "openAt", qualifiedByName = "openAt")
    @Mapping(source = "tillEntity", target = "closedAt", qualifiedByName = "closedAt")
    Till domainFromEntity(TillEntity tillEntity);

    @Named("openAt")
    default ZonedDateTime openAtEntityToDomain(TillEntity tillEntity) {
        return tillEntity.getOpenAt().atZone(ZoneId.of("UTC").normalized());
    }

    @Named("closedAt")
    default ZonedDateTime closedAtDomainToEntity(TillEntity tillEntity) {
        return tillEntity.getClosedAt().atZone(ZoneId.of("UTC").normalized());
    }

}

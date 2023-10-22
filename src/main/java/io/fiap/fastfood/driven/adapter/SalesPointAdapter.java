package io.fiap.fastfood.driven.adapter;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.salespoint.mapper.SalesPointMapper;
import io.fiap.fastfood.driven.core.domain.salespoint.port.outbound.SalesPointPort;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.NotFoundException;
import io.fiap.fastfood.driven.repository.SalesPointRepository;
import io.fiap.fastfood.driver.controller.dto.SalesPointDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;

@Component
public class SalesPointAdapter implements SalesPointPort {

    private final SalesPointRepository salesPointRepository;
    private final SalesPointMapper mapper;

    public SalesPointAdapter(SalesPointRepository salesPointRepository,
                             SalesPointMapper mapper) {
        this.salesPointRepository = salesPointRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<SalesPoint> createSalesPoint(SalesPoint salesPoint) {
        return salesPointRepository.save(mapper.entityFromDomain(salesPoint))
                .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<SalesPoint> findSalesPoint(String id) {
        return salesPointRepository.findById(id)
                .map(mapper::domainFromEntity)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException())));
    }

    @Override
    public Mono<SalesPoint> updateSalesPoint(String id, SalesPointDTO salesPointDTO) {
        return salesPointRepository.findById(id)
                .map(salesPointEntity -> updateEntity(salesPointEntity, salesPointDTO))
                .flatMap(salesPointRepository::save)
                .map(mapper::domainFromEntity);
    }

    private static SalesPointEntity updateEntity(SalesPointEntity salesPointEntity, SalesPointDTO salesPointDTO) {

        if (ObjectUtils.isNotEmpty(salesPointDTO.id())) {
            throw new BadRequestException("Salespoint id can't be changed.");
        }

        if (ObjectUtils.isNotEmpty(salesPointDTO.description())) {
            salesPointEntity.setDescription(salesPointDTO.description());
        }

        IntStream.range(0, salesPointEntity.getTills().size())
                .forEach(index -> {
                    if (ObjectUtils.isNotEmpty(salesPointDTO.tills().get(index).openAt())) {
                        salesPointEntity.getTills().get(index).setOpenAt(
                                ZonedDateTime.parse(salesPointDTO.tills().get(index).openAt()).toLocalDateTime());
                    }

                    if (ObjectUtils.isNotEmpty(salesPointDTO.tills().get(index).closedAt())) {
                        salesPointEntity.getTills().get(index).setClosedAt(ZonedDateTime
                                .parse(salesPointDTO.tills().get(index).closedAt()).toLocalDateTime());
                    }
                });


        return salesPointEntity;
    }

    @Override
    public Mono<Void> deleteSalesPoint(String id) {
        return salesPointRepository.deleteById(id);
    }

}

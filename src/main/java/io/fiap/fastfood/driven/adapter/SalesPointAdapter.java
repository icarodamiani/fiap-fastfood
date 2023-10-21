package io.fiap.fastfood.driven.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.salespoint.mapper.SalesPointDomainMapper;
import io.fiap.fastfood.driven.core.domain.salespoint.mapper.SalesPointMapper;
import io.fiap.fastfood.driven.core.domain.salespoint.port.outbound.SalesPointPort;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.repository.SalesPointRepository;
import io.fiap.fastfood.driven.repository.TillRepository;
import io.vavr.CheckedFunction2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class SalesPointAdapter implements SalesPointPort {

    private final SalesPointRepository salesPointRepository;
    private final TillRepository tillRepository;
    private final SalesPointMapper mapper;
    private final SalesPointDomainMapper salesPointDomainMapper;
    private final ObjectMapper objectMapper;

    public SalesPointAdapter(SalesPointRepository salesPointRepository,
                             TillRepository tillRepository, SalesPointMapper mapper,
                             SalesPointDomainMapper salesPointDomainMapper,
                             ObjectMapper objectMapper) {
        this.salesPointRepository = salesPointRepository;
        this.tillRepository = tillRepository;
        this.mapper = mapper;
        this.salesPointDomainMapper = salesPointDomainMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<SalesPoint> createSalesPoint(SalesPoint salesPoint) {
        return salesPointRepository.save(mapper.entityFromDomain(salesPoint))
                .zipWhen(salesPointEntity -> tillRepository.findByIdSalesPoint(salesPointEntity.getId())
                        .collectList()
                        .defaultIfEmpty(Collections.emptyList()))
                .map(tuple2 -> salesPointDomainMapper.domainFromEntity(tuple2.getT1(), tuple2.getT2()));
    }

    @Override
    public Mono<SalesPoint> findSalesPoint(Long id) {
        return tillRepository.findByIdSalesPoint(id)
                .collectList()
                .defaultIfEmpty(Collections.emptyList())
                .zipWith(salesPointRepository.findById(id))
                .map(tuple2 -> salesPointDomainMapper.domainFromEntity(tuple2.getT2(), tuple2.getT1()));
    }

    @Override
    public Mono<SalesPoint> updateSalesPoint(Long id, JsonPatch operations) {
        return salesPointRepository.findById(id)
                .map(salesPoint -> applyPatch().unchecked().apply(salesPoint, operations))
                .flatMap(salesPointRepository::save)
                .zipWhen(salesPointEntity -> tillRepository.findByIdSalesPoint(salesPointEntity.getId())
                        .collectList()
                        .defaultIfEmpty(Collections.emptyList()))
                .map(tuple2 -> salesPointDomainMapper.domainFromEntity(tuple2.getT1(), tuple2.getT2()))
                .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    // Quando deletar o ponto de venda... precisa deletar todos os caixas relacionados a ele primeiro...
    @Override
    public Mono<Void> deleteSalesPoint(Long id) {
        return tillRepository.deleteAllByIdSalesPoint(id)
                .then(salesPointRepository.deleteById(id));
    }

    private CheckedFunction2<SalesPointEntity, JsonPatch, SalesPointEntity> applyPatch() {
        return (salesPoint, operations) -> {
            var patched = operations.apply(objectMapper.convertValue(salesPoint, JsonNode.class));
            return objectMapper.treeToValue(patched, SalesPointEntity.class);
        };
    }
}

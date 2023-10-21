package io.fiap.fastfood.driven.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.salespoint.mapper.SalesPointMapper;
import io.fiap.fastfood.driven.core.domain.salespoint.port.outbound.SalesPointPort;
import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.repository.SalesPointRepository;
import io.vavr.CheckedFunction2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SalesPointAdapter implements SalesPointPort {

    private final SalesPointRepository salesPointRepository;
    private final SalesPointMapper mapper;
    private final ObjectMapper objectMapper;

    public SalesPointAdapter(SalesPointRepository salesPointRepository,
                             SalesPointMapper mapper,
                             ObjectMapper objectMapper) {
        this.salesPointRepository = salesPointRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<SalesPoint> createProduct(SalesPoint product) {
        return salesPointRepository.save(mapper.entityFromDomain(product))
                .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<SalesPoint> findSalesPoint(Long id) {
        return salesPointRepository.findById(id)
                .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<SalesPoint> updateProduct(Long id, JsonPatch operations) {
        return salesPointRepository.findById(id)
                .map(salesPoint -> applyPatch().unchecked().apply(salesPoint, operations))
                .flatMap(salesPointRepository::save)
                .map(mapper::domainFromEntity)
                .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return salesPointRepository.deleteById(id);
    }

    private CheckedFunction2<SalesPointEntity, JsonPatch, SalesPointEntity> applyPatch() {
        return (salesPoint, operations) -> {
            var patched = operations.apply(objectMapper.convertValue(salesPoint, JsonNode.class));
            return objectMapper.treeToValue(patched, SalesPointEntity.class);
        };
    }
}

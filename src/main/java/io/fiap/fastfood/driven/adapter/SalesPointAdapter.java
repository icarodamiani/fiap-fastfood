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
import io.fiap.fastfood.driven.core.exception.NotFoundException;
import io.fiap.fastfood.driven.repository.SalesPointRepository;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
    public Mono<SalesPoint> updateSalesPoint(String id, String operations) {
        return salesPointRepository.findById(id)
                .map(salesPoint -> applyPatch().unchecked().apply(salesPoint, operations))
                .flatMap(salesPointRepository::save)
                .map(mapper::domainFromEntity)
                .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    @Override
    public Mono<Void> deleteSalesPoint(String id) {
        return salesPointRepository.deleteById(id);
    }

    private CheckedFunction2<SalesPointEntity, String, SalesPointEntity> applyPatch() {
        return (salesPoint, operations) -> {
            var patch = readOperations()
                    .unchecked()
                    .apply(operations);

            var patched = patch.apply(objectMapper.convertValue(salesPoint, JsonNode.class));

            return objectMapper.treeToValue(patched, SalesPointEntity.class);
        };
    }

    private CheckedFunction1<String, JsonPatch> readOperations() {
        return operations -> {
            final InputStream in = new ByteArrayInputStream(operations.getBytes());
            return objectMapper.readValue(in, JsonPatch.class);
        };
    }
}

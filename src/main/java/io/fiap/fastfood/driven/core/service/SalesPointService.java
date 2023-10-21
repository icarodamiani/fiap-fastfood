package io.fiap.fastfood.driven.core.service;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.salespoint.port.inbound.SalesPointUseCase;
import io.fiap.fastfood.driven.core.domain.salespoint.port.outbound.SalesPointPort;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SalesPointService implements SalesPointUseCase {

    private final SalesPointPort salesPointPort;

    public SalesPointService(SalesPointPort salesPointPort) {
        this.salesPointPort = salesPointPort;
    }

    @Override
    public Mono<SalesPoint> create(SalesPoint salesPoint) {
        return Mono.just(salesPoint)
                .flatMap(salesPointPort::createProduct)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())));
    }

    @Override
    public Mono<SalesPoint> find(Long id) {
        return salesPointPort.findSalesPoint(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.just(id)
                .flatMap(salesPointPort::deleteProduct)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())));
    }

    @Override
    public Mono<SalesPoint> update(Long id, JsonPatch operations) {
        return salesPointPort.updateProduct(id, operations);
    }
}

package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driven.core.domain.salespoint.port.inbound.SalesPointUseCase;
import io.fiap.fastfood.driven.core.domain.salespoint.port.outbound.SalesPointPort;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driver.controller.salespoint.dto.SalesPointDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
                .flatMap(salesPointPort::createSalesPoint);
    }

    @Override
    public Flux<SalesPoint> find(String id, Pageable pageable) {
        return salesPointPort.findSalesPoint(id, pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return salesPointPort.deleteSalesPoint(id);
    }

    @Override
    public Mono<SalesPoint> update(String id, SalesPointDTO salesPointDTO) {
        return salesPointPort.updateSalesPoint(id, salesPointDTO);
    }
}

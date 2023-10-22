package io.fiap.fastfood.driven.core.domain.salespoint.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import reactor.core.publisher.Mono;

public interface SalesPointPort {
    Mono<SalesPoint> createSalesPoint(SalesPoint product);

    Mono<SalesPoint> findSalesPoint(String id);

    Mono<SalesPoint> updateSalesPoint(String id, String operations);

    Mono<Void> deleteSalesPoint(String id);

}

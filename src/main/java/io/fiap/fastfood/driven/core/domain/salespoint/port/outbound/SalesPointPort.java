package io.fiap.fastfood.driven.core.domain.salespoint.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driver.controller.dto.SalesPointDTO;
import reactor.core.publisher.Mono;

public interface SalesPointPort {
    Mono<SalesPoint> createSalesPoint(SalesPoint product);

    Mono<SalesPoint> findSalesPoint(String id);

    Mono<SalesPoint> updateSalesPoint(String id, SalesPointDTO salesPointDTO);

    Mono<Void> deleteSalesPoint(String id);

}

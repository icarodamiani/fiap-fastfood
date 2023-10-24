package io.fiap.fastfood.driven.core.domain.salespoint.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driver.controller.product.dto.SalesPointDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesPointPort {
    Mono<SalesPoint> createSalesPoint(SalesPoint product);

    Flux<SalesPoint> findSalesPoint(String id, Pageable pageable);

    Mono<SalesPoint> updateSalesPoint(String id, SalesPointDTO salesPointDTO);

    Mono<Void> deleteSalesPoint(String id);

}

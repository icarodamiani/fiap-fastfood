package io.fiap.fastfood.driven.core.domain.salespoint.port.outbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import reactor.core.publisher.Mono;

public interface SalesPointPort {
    Mono<SalesPoint> createSalesPoint(SalesPoint product);

    Mono<SalesPoint> findSalesPoint(Long id);

    Mono<SalesPoint> updateSalesPoint(Long id, JsonPatch operations);

    Mono<Void> deleteSalesPoint(Long id);

}

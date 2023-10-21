package io.fiap.fastfood.driven.core.domain.salespoint.port.outbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import reactor.core.publisher.Mono;

public interface SalesPointPort {
    Mono<SalesPoint> createProduct(SalesPoint product);

    Mono<SalesPoint> findSalesPoint(Long id);

    Mono<SalesPoint> updateProduct(Long id, JsonPatch operations);

    Mono<Void> deleteProduct(Long id);

}

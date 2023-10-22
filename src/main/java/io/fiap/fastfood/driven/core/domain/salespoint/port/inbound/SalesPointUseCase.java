package io.fiap.fastfood.driven.core.domain.salespoint.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import reactor.core.publisher.Mono;

public interface SalesPointUseCase {
    Mono<SalesPoint> create(SalesPoint salesPoint);

    Mono<SalesPoint> find(String id);

    Mono<Void> delete(String id);

    Mono<SalesPoint> update(String id, String operations);
}

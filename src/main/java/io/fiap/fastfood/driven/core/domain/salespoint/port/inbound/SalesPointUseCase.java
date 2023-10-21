package io.fiap.fastfood.driven.core.domain.salespoint.port.inbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import reactor.core.publisher.Mono;

public interface SalesPointUseCase {
    Mono<SalesPoint> create(SalesPoint salesPoint);

    Mono<SalesPoint> find(Long id);

    Mono<Void> delete(Long id);

    Mono<SalesPoint> update(Long id, JsonPatch operations);
}

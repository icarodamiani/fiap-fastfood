package io.fiap.fastfood.driven.core.domain.salespoint.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.SalesPoint;
import io.fiap.fastfood.driver.controller.product.dto.SalesPointDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SalesPointUseCase {
    Mono<SalesPoint> create(SalesPoint salesPoint);

    Flux<SalesPoint> find(String id, Pageable pageable);

    Mono<Void> delete(String id);

    Mono<SalesPoint> update(String id, SalesPointDTO operations);
}

package io.fiap.fastfood.core.domain.product.port.inbound;

import reactor.core.publisher.Mono;

public interface DeleteProductUseCase {
    Mono<Void> delete(String id);
}

package io.fiap.fastfood.core.domain.product.port.outbound;

import reactor.core.publisher.Mono;

public interface DeleteProductPort {

    Mono<Void> deleteProduct(String id);
}

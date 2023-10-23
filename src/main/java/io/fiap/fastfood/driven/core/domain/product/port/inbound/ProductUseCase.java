package io.fiap.fastfood.driven.core.domain.product.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> create(Product value);

    Flux<Product> list(Long typeId, Pageable pageable);

    Mono<Void> delete(Long id);

    Mono<Product> update(Long id, String operations);
}

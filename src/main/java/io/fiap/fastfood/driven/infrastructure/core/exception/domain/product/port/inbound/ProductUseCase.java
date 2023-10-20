package io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.port.inbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> create(Product value);
    Flux<Product> list(String typeId, Pageable pageable);
    Mono<Void> delete(String id);
    Mono<Product> update(String id, JsonPatch operations);
}

package io.fiap.fastfood.core.domain.product.port.inbound;

import io.fiap.fastfood.core.domain.model.Product;
import reactor.core.publisher.Mono;

public interface CreateProductUseCase {
    Mono<Product> create(Product value);
}

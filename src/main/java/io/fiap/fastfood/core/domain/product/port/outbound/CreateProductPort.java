package io.fiap.fastfood.core.domain.product.port.outbound;

import io.fiap.fastfood.core.domain.model.Product;
import reactor.core.publisher.Mono;

public interface CreateProductPort {

    Mono<Product> createProduct(Product product);
}

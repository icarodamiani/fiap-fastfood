package io.fiap.fastfood.core.domain.product.port.inbound;

import io.fiap.fastfood.core.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface ListProductUseCase {
    Flux<Product> list(String typeId, Pageable pageable);
}

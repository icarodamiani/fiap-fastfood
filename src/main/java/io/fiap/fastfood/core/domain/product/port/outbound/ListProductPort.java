package io.fiap.fastfood.core.domain.product.port.outbound;

import io.fiap.fastfood.core.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface ListProductPort {

    Flux<Product> listProduct(String typeId, Pageable pageable);
}

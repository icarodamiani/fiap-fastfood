package io.fiap.fastfood.driven.core.exception.domain.product.port.outbound;

import io.fiap.fastfood.driven.core.exception.domain.model.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductPort {

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Long id, String operations);

    Mono<Void> deleteProduct(Long id);

    Flux<Product> listProduct(Long typeId, Pageable pageable);
}

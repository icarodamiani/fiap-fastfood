package io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.port.outbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductPort {

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(String id, JsonPatch operations);

    Mono<Void> deleteProduct(String id);

    Flux<Product> listProduct(String typeId, Pageable pageable);
}

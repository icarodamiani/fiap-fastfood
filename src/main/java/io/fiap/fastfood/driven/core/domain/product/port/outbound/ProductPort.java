package io.fiap.fastfood.driven.core.domain.product.port.outbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.domain.model.Product;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductPort {

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Long id, JsonPatch operations);

    Mono<Void> deleteProduct(Long id);

    Flux<Product> listProduct(Long typeId, Pageable pageable);
}

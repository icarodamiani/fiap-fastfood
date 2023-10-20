package io.fiap.fastfood.core.domain.product.port.inbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.core.domain.model.Product;
import reactor.core.publisher.Mono;

public interface UpdateProductUseCase {
    Mono<Product> update(String id, JsonPatch patchOperations);
}

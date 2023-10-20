package io.fiap.fastfood.core.domain.product.port.outbound;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.core.domain.model.Product;
import reactor.core.publisher.Mono;

public interface UpdateProductPort {

    Mono<Product> updateProduct(String id, JsonPatch operations);
}

package io.fiap.fastfood.driven.infrastructure.core.service;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.model.Product;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.port.inbound.ProductUseCase;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.port.outbound.ProductPort;
import io.fiap.fastfood.driven.infrastructure.core.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements ProductUseCase {

    private ProductPort productPort;


    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @Override
    public Mono<Product> create(Product product) {
        return Mono.just(product)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(p -> productPort.createProduct(p));
    }

    @Override
    public Flux<Product> list(String typeId, Pageable pageable) {
        return productPort.listProduct(typeId, pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(v -> productPort.deleteProduct(v));
    }


    @Override
    public Mono<Product> update(String id, JsonPatch operations) {
        return productPort.updateProduct(id, operations);
    }

}

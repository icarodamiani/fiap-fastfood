package io.fiap.fastfood.driven.core.service;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.domain.model.Product;
import io.fiap.fastfood.driven.core.exception.domain.product.port.inbound.ProductUseCase;
import io.fiap.fastfood.driven.core.exception.domain.product.port.outbound.ProductPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements ProductUseCase {

    private final ProductPort productPort;


    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @Override
    public Mono<Product> create(Product product) {
        return Mono.just(product)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::createProduct);
    }

    @Override
    public Flux<Product> list(Long typeId, Pageable pageable) {
        return productPort.listProduct(typeId, pageable);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::deleteProduct);
    }


    @Override
    public Mono<Product> update(Long id, JsonPatch operations) {
        return productPort.updateProduct(id, operations);
    }

}

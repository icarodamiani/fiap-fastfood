package io.fiap.fastfood.core.service;

import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.inbound.CreateProductUseCase;
import io.fiap.fastfood.core.domain.product.port.outbound.CreateProductPort;
import io.fiap.fastfood.core.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateProductService implements CreateProductUseCase {

    private CreateProductPort createProductPort;

    public CreateProductService(CreateProductPort createProductPort) {
        this.createProductPort = createProductPort;
    }

    @Override
    public Mono<Product> create(Product product) {
        return Mono.just(product)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(p -> createProductPort.createProduct(p));
    }
}

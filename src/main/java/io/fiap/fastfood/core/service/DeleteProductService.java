package io.fiap.fastfood.core.service;

import io.fiap.fastfood.core.domain.product.port.inbound.DeleteProductUseCase;
import io.fiap.fastfood.core.domain.product.port.outbound.DeleteProductPort;
import io.fiap.fastfood.core.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteProductService implements DeleteProductUseCase {
    private DeleteProductPort deleteProductPort;

    public DeleteProductService(DeleteProductPort deleteProductPort) {
        this.deleteProductPort = deleteProductPort;
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(v -> deleteProductPort.deleteProduct(v));
    }
}

package io.fiap.fastfood.driven.infrastructure.adapter;

import io.fiap.fastfood.core.domain.product.port.outbound.DeleteProductPort;
import io.fiap.fastfood.driven.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DeleteProductAdapter implements DeleteProductPort {
    private ProductRepository productRepository;

    public DeleteProductAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(Long.valueOf(id));
    }
}

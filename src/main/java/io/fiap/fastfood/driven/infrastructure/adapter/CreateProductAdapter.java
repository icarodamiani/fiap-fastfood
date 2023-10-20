package io.fiap.fastfood.driven.infrastructure.adapter;

import io.fiap.fastfood.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.outbound.CreateProductPort;
import io.fiap.fastfood.driven.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateProductAdapter implements CreateProductPort {
    private ProductRepository productRepository;
    private ProductMapper mapper;

    public CreateProductAdapter(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    //TODO validar se tipo do produto existe, caso contrário inserir antes do produto.
    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(mapper.entityFromDomain(product))
            .map(p -> mapper.domainFromEntity(p));
    }
}

package io.fiap.fastfood.driven.infrastructure.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.outbound.UpdateProductPort;
import io.fiap.fastfood.core.entity.ProductEntity;
import io.fiap.fastfood.driven.infrastructure.repository.ProductRepository;
import io.vavr.CheckedFunction2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateProductAdapter implements UpdateProductPort {
    private ProductRepository productRepository;
    private ProductMapper mapper;
    private ObjectMapper objectMapper;

    public UpdateProductAdapter(ProductRepository productRepository, ProductMapper mapper, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Product> updateProduct(String id, JsonPatch operations) {
        return productRepository.findById(Long.valueOf(id))
            .map(product -> applyPatch().unchecked().apply(product, operations))
            .flatMap(patched -> productRepository.save(patched))
            .map(mapper::domainFromEntity);
    }

    private CheckedFunction2<ProductEntity, JsonPatch, ProductEntity> applyPatch() {
        return (product, operations) -> {
            var patched = operations.apply(objectMapper.convertValue(product, JsonNode.class));
            return objectMapper.treeToValue(patched, ProductEntity.class);
        };
    }
}

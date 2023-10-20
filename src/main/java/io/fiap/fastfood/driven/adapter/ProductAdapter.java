package io.fiap.fastfood.driven.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.core.entity.ProductEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.domain.model.Product;
import io.fiap.fastfood.driven.core.exception.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.driven.core.exception.domain.product.mapper.ProductTypeMapper;
import io.fiap.fastfood.driven.core.exception.domain.product.port.outbound.ProductPort;
import io.fiap.fastfood.driven.repository.ProductRepository;
import io.fiap.fastfood.driven.repository.ProductTypeRepository;
import io.vavr.CheckedFunction2;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductAdapter implements ProductPort {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductMapper mapper;
    private final ProductTypeMapper typeMapper;
    private final ObjectMapper objectMapper;

    public ProductAdapter(ProductRepository productRepository,
                          ProductTypeRepository productTypeRepository, ProductMapper mapper,
                          ProductTypeMapper typeMapper, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.mapper = mapper;
        this.typeMapper = typeMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return productTypeRepository.save(typeMapper.entityFromDomain(product.type()))
            .then(productRepository.save(mapper.entityFromDomain(product)))
            .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Mono<Product> updateProduct(Long id, JsonPatch operations) {
        return productRepository.findById(id)
            .map(product -> applyPatch().unchecked().apply(product, operations))
            .flatMap(productRepository::save)
            .map(mapper::domainFromEntity)
            .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    private CheckedFunction2<ProductEntity, JsonPatch, ProductEntity> applyPatch() {
        return (product, operations) -> {
            var patched = operations.apply(objectMapper.convertValue(product, JsonNode.class));
            return objectMapper.treeToValue(patched, ProductEntity.class);
        };
    }

    @Override
    public Flux<Product> listProduct(Long typeId, Pageable pageable) {
        return Flux.just(Optional.ofNullable(typeId))
            .filter(Optional::isEmpty)
            .flatMap(__ -> productRepository.findByIdNotNull(pageable))
            .switchIfEmpty(Flux.defer(() -> productRepository.findByTypeId(typeId, pageable)))
            .flatMap(entity -> productTypeRepository.findById(entity.getTypeId())
                .map(productTypeEntity -> {
                        entity.setType(productTypeEntity);
                        return entity;
                    }
                ))
            .map(mapper::domainFromEntity);
    }
}

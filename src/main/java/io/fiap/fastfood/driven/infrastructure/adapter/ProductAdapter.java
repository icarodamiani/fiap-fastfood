package io.fiap.fastfood.driven.infrastructure.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.model.Product;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.driven.infrastructure.core.exception.domain.product.port.outbound.ProductPort;
import io.fiap.fastfood.driven.infrastructure.core.entity.ProductEntity;
import io.fiap.fastfood.driven.infrastructure.repository.ProductRepository;
import io.fiap.fastfood.driven.infrastructure.repository.ProductTypeRepository;
import io.vavr.CheckedFunction2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductAdapter implements ProductPort {
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;
    private ProductMapper mapper;
    private ObjectMapper objectMapper;

    public ProductAdapter(ProductRepository productRepository,
                          ProductTypeRepository productTypeRepository, ProductMapper mapper, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }


    //TODO validar se tipo do produto existe, caso contrário inserir antes do produto.
    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(mapper.entityFromDomain(product))
            .map(p -> mapper.domainFromEntity(p));
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(Long.valueOf(id));
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

    @Override
    public Flux<Product> listProduct(String typeId, Pageable pageable) {
        return Flux.just(typeId)
            .flatMap(t -> productRepository.findByTypeId(t, pageable))
            .switchIfEmpty(Flux.defer(() -> productRepository.findByIdNotNull(pageable)
                .flatMap(entity -> productTypeRepository.findById(Long.valueOf(entity.getTypeId()))
                    .map(productTypeEntity -> {
                            entity.setType(productTypeEntity);
                            return entity;
                        }
                    ))))
            .map(mapper::domainFromEntity);
    }
}

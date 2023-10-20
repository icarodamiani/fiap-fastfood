package io.fiap.fastfood.driven.infrastructure.adapter;

import io.fiap.fastfood.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.outbound.ListProductPort;
import io.fiap.fastfood.driven.infrastructure.repository.ProductRepository;
import io.fiap.fastfood.driven.infrastructure.repository.ProductTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ListProductAdapter implements ListProductPort {
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;
    private ProductMapper mapper;

    public ListProductAdapter(ProductRepository productRepository, ProductTypeRepository productTypeRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.mapper = mapper;
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

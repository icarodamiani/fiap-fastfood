package io.fiap.fastfood.core.service;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.inbound.UpdateProductUseCase;
import io.fiap.fastfood.core.domain.product.port.outbound.UpdateProductPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateProductService implements UpdateProductUseCase {

    private ProductMapper mapper;

    private UpdateProductPort updateProductPort;

    public UpdateProductService(ProductMapper mapper, UpdateProductPort updateProductPort) {
        this.mapper = mapper;
        this.updateProductPort = updateProductPort;
    }

    @Override
    public Mono<Product> update(String id, JsonPatch operations) {
        return updateProductPort.updateProduct(id, operations);
    }
}

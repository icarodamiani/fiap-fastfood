package io.fiap.fastfood.core.service;

import io.fiap.fastfood.core.domain.model.Product;
import io.fiap.fastfood.core.domain.product.port.inbound.ListProductUseCase;
import io.fiap.fastfood.core.domain.product.port.outbound.ListProductPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ListProductService implements ListProductUseCase {

    private ListProductPort listProductPort;

    @Override
    public Flux<Product> list(String typeId, Pageable pageable) {
        return listProductPort.listProduct(typeId, pageable);
    }
}

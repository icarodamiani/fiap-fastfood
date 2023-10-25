package io.fiap.fastfood.driven.core.exception.domain.order.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.Order;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderUseCase {
    Mono<Order> create(Order value);

    Flux<Order> list(Long id, Pageable pageable);

    Mono<Void> delete(Long id);

    Mono<Order> update(Long id, String operations);
}

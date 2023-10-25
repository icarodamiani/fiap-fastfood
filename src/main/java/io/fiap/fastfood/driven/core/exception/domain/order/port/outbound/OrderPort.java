package io.fiap.fastfood.driven.core.exception.domain.order.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.Order;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderPort {

    Mono<Order> createOrder(Order order);

    Mono<Order> updateOrder(Long id, String operations);

    Mono<Void> deleteOrder(Long id);

    Flux<Order> listOrder(Long id, Pageable pageable);
}

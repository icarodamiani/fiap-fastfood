package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.model.Order;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.domain.order.port.inbound.OrderUseCase;
import io.fiap.fastfood.driven.core.exception.domain.order.port.outbound.OrderPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService implements OrderUseCase {

    private final OrderPort orderPort;


    public OrderService(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @Override
    public Mono<Order> create(Order order) {
        return Mono.just(order)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(orderPort::createOrder);
    }

    @Override
    public Flux<Order> list(Long id, Pageable pageable) {
        return orderPort.listOrder(id, pageable);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(orderPort::deleteOrder);
    }


    @Override
    public Mono<Order> update(Long id, String operations) {
        return orderPort.updateOrder(id, operations);
    }

}

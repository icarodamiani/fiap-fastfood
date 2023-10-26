package io.fiap.fastfood.driven.core.domain.order.tracking.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderTrackingPort {
    Mono<OrderTracking> createOrderTracking(OrderTracking orderTracking);

    Mono<OrderTracking> findOrderTracking(String orderId);

    Flux<OrderTracking> findAllOrders(Pageable pageable);

}

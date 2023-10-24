package io.fiap.fastfood.driven.core.domain.order.tracking.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import reactor.core.publisher.Mono;

public interface OrderTrackingUseCase {
    Mono<OrderTracking> create(OrderTracking orderTracking);

    Mono<OrderTracking> find(String orderId);

}

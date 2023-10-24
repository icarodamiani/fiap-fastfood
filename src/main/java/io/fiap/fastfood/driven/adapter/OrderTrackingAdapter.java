package io.fiap.fastfood.driven.adapter;

import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import io.fiap.fastfood.driven.core.domain.order.tracking.mapper.OrderTrackingMapper;
import io.fiap.fastfood.driven.core.domain.order.tracking.port.outbound.OrderTrackingPort;
import io.fiap.fastfood.driven.core.exception.NotFoundException;
import io.fiap.fastfood.driven.repository.OrderTrackingRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderTrackingAdapter implements OrderTrackingPort {

    private final OrderTrackingRepository orderTrackingRepository;
    private final OrderTrackingMapper orderTrackingMapper;

    public OrderTrackingAdapter(OrderTrackingRepository orderTrackingRepository,
                                OrderTrackingMapper orderTrackingMapper) {
        this.orderTrackingRepository = orderTrackingRepository;
        this.orderTrackingMapper = orderTrackingMapper;
    }

    @Override
    public Mono<OrderTracking> createOrderTracking(OrderTracking orderTracking) {
        return orderTrackingRepository.save(orderTrackingMapper.entityFromDomain(orderTracking))
                .map(orderTrackingMapper::domainFromEntity);
    }

    @Override
    public Mono<OrderTracking> findOrderTracking(String orderId) {
        return orderTrackingRepository.findByOrderIdOrderByOrderDateTime(orderId)
                .single()
                .map(orderTrackingMapper::domainFromEntity)
                .switchIfEmpty(Mono.defer(() -> Mono.error(NotFoundException::new)));
    }
}

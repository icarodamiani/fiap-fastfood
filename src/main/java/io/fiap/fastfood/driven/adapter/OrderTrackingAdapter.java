package io.fiap.fastfood.driven.adapter;

import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import io.fiap.fastfood.driven.core.domain.order.tracking.mapper.OrderTrackingMapper;
import io.fiap.fastfood.driven.core.domain.order.tracking.port.outbound.OrderTrackingPort;
import io.fiap.fastfood.driven.core.entity.OrderTrackingEntity;
import io.fiap.fastfood.driven.core.exception.NotFoundException;
import io.fiap.fastfood.driven.repository.OrderTrackingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Component
public class OrderTrackingAdapter implements OrderTrackingPort {

    private final OrderTrackingRepository orderTrackingRepository;
    private final OrderTrackingMapper orderTrackingMapper;

    private final ReactiveMongoTemplate mongoTemplate;

    public OrderTrackingAdapter(OrderTrackingRepository orderTrackingRepository,
                                OrderTrackingMapper orderTrackingMapper, ReactiveMongoTemplate mongoTemplate) {
        this.orderTrackingRepository = orderTrackingRepository;
        this.orderTrackingMapper = orderTrackingMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<OrderTracking> createOrderTracking(OrderTracking orderTracking) {
        return orderTrackingRepository.save(orderTrackingMapper.entityFromDomain(orderTracking))
                .map(orderTrackingMapper::domainFromEntity);
    }

    @Override
    public Mono<OrderTracking> findOrderTracking(String orderId) {
        return orderTrackingRepository.findByOrderIdOrderByOrderDateTime(orderId)
                .last()
                .map(orderTrackingMapper::domainFromEntity)
                .switchIfEmpty(Mono.defer(() -> Mono.error(NotFoundException::new)));
    }

    @Override
    public Flux<OrderTracking> findAllOrders(Pageable pageable) {
        return findOrderGroup(pageable)
                .map(orderTrackingMapper::domainFromEntity)
                .switchIfEmpty(Mono.defer(() -> Mono.error(NotFoundException::new)));
    }

    private Flux<OrderTrackingEntity> findOrderGroup(Pageable pageable) {
        Aggregation aggregationFirst = Aggregation.newAggregation(
                Aggregation.group("numero_pedido")
                        .first("$$ROOT").as("orderEntityFirst"),
                Aggregation.replaceRoot("orderEntityFirst")
        );

        Aggregation aggregationLast = Aggregation.newAggregation(
                Aggregation.group("numero_pedido")
                        .last("$$ROOT").as("orderEntityLast"),
                Aggregation.replaceRoot("orderEntityLast")
        );

        Flux<OrderTrackingEntity> firsts = mongoTemplate
                .aggregate(aggregationFirst, "pedido_acompanhamento", OrderTrackingEntity.class);

        Flux<OrderTrackingEntity> lasts = mongoTemplate
                .aggregate(aggregationLast, "pedido_acompanhamento", OrderTrackingEntity.class);

        return Flux.concat(getLasts(firsts, lasts), getFirsts(firsts))
                .sort(Comparator.comparing(OrderTrackingEntity::orderDateTime)
                        .thenComparing(OrderTrackingEntity::orderNumber));
    }

    private Flux<OrderTrackingEntity> getFirsts(Flux<OrderTrackingEntity> firsts) {
        return firsts
                .map(order -> OrderTrackingEntity.OrderTrackingEntityBuilder
                        .builder()
                        .withId(order.id())
                        .withOrderId(order.orderId())
                        .withOrderStatus(order.orderStatus())
                        .withOrderNumber(order.orderNumber())
                        .withRole(order.role())
                        .withOrderDateTime(order.orderDateTime())
                        .withOrderTimeSpent(0L)
                        .build());
    }

    private Flux<OrderTrackingEntity> getLasts(Flux<OrderTrackingEntity> firsts, Flux<OrderTrackingEntity> lasts) {
        return lasts.flatMap(orderTrackingEntity -> firsts.filter(order ->
                        orderTrackingEntity.orderNumber()
                                .equals(order.orderNumber()))
                .map(order -> OrderTrackingEntity.OrderTrackingEntityBuilder
                        .builder()
                        .withId(orderTrackingEntity.id())
                        .withOrderId(orderTrackingEntity.orderId())
                        .withOrderStatus(orderTrackingEntity.orderStatus())
                        .withOrderNumber(orderTrackingEntity.orderNumber())
                        .withRole(orderTrackingEntity.role())
                        .withOrderDateTime(orderTrackingEntity.orderDateTime())
                        .withOrderTimeSpent(order.orderDateTime()
                                .until(orderTrackingEntity.orderDateTime(), ChronoUnit.MINUTES))
                        .build())
        );
    }
}

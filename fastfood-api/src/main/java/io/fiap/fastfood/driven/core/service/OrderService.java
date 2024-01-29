package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.counter.port.outbound.CounterPort;
import io.fiap.fastfood.driven.core.domain.model.Order;
import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.domain.order.port.inbound.OrderUseCase;
import io.fiap.fastfood.driven.core.domain.order.port.outbound.OrderPort;
import io.fiap.fastfood.driven.core.domain.payment.port.outbound.PaymentPort;
import io.fiap.fastfood.driven.core.domain.tracking.port.outbound.OrderTrackingPort;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService implements OrderUseCase {

    private final OrderPort orderPort;
    private final PaymentPort paymentPort;
    private final CounterPort counterPort;
    private final OrderTrackingPort trackingPort;

    public OrderService(OrderPort orderPort, PaymentPort paymentPort, CounterPort counterPort, OrderTrackingPort trackingPort) {
        this.orderPort = orderPort;
        this.paymentPort = paymentPort;
        this.counterPort = counterPort;
        this.trackingPort = trackingPort;
    }

    @Override
    public Mono<Order> create(Order order) {
        return Mono.just(order)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .zipWith(counterPort.nextSequence("order_number_seq"))
            .flatMap(t ->
                orderPort.createOrder(
                    Order.OrderBuilder
                        .from(order)
                        .withNumber(t.getT2())
                        .build())
            )
            .flatMap(o ->
                paymentPort.createPayment(Payment.PaymentBuilder.from(order.payment())
                        .withOrderId(o.id())
                        .withDateTime(LocalDateTime.now())
                        .build())
                    .flatMap(p -> this.createWaitingPaymentStatus(p, o.number()))
                    .map(payment -> o)
            );
    }

    private Mono<OrderTracking> createWaitingPaymentStatus(Payment payment, Long orderNumber) {
        return trackingPort.createOrderTracking(OrderTracking.OrderTrackingBuilder.builder()
            .withRole("EMPLOYEE")
            .withOrderId(payment.orderId())
            .withOrderNumber(String.valueOf(orderNumber))
            .withOrderDateTime(LocalDateTime.now())
            .withOrderStatus("WAITING_PAYMENT")
            .build());
    }


    @Override
    public Flux<Order> list(String id, Pageable pageable) {
        return orderPort.listOrder(id, pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(orderPort::deleteOrder);
    }


    @Override
    public Mono<Order> update(String id, String operations) {
        return orderPort.updateOrder(id, operations);
    }

}

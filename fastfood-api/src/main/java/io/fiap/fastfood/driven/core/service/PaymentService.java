package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.model.OrderTracking;
import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.domain.payment.port.inbound.PaymentUseCase;
import io.fiap.fastfood.driven.core.domain.tracking.port.outbound.OrderTrackingPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService implements PaymentUseCase {

    private final OrderTrackingPort orderTrackingPort;

    public PaymentService(OrderTrackingPort orderTrackingPort) {
        this.orderTrackingPort = orderTrackingPort;
    }

    @Override
    public Mono<Void> updateAsPaid(Payment payment) {
        return orderTrackingPort.findByOrderId(payment.orderId())
            .map(tracking -> OrderTracking.OrderTrackingBuilder.from(tracking)
                .withId(null)
                .withOrderStatus("PAYMENT_CONFIRMED")
                .withOrderStatusValue("2")
                .build())
            .flatMap(orderTrackingPort::createOrderTracking)
            .then();
    }
}

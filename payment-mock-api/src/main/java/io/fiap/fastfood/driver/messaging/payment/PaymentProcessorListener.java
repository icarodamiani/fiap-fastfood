package io.fiap.fastfood.driver.messaging.payment;

import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.domain.payment.port.inbound.PaymentUseCase;
import io.fiap.fastfood.driven.core.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorListener {

    private PaymentUseCase paymentUseCase;

    public PaymentProcessorListener(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @RabbitListener(queues = "PAYMENT")
    public void listen(Payment payment) {
        paymentUseCase.processAndNotify(payment)
            .subscribe();
    }
}

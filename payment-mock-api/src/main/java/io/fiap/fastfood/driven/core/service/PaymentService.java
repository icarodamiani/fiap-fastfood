package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.domain.payment.port.inbound.PaymentUseCase;
import io.fiap.fastfood.driven.core.domain.payment.port.outbound.PaymentPort;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService implements PaymentUseCase {

    private final PaymentPort paymentPort;
    private WebClient webClient;

    public PaymentService(PaymentPort paymentPort, WebClient webClient) {
        this.paymentPort = paymentPort;
        this.webClient = webClient;
    }

    @Override
    public Mono<Payment> createPayment(Payment payment) {
        return paymentPort.createPayment(payment);
    }

    @Override
    public Mono<Payment> processAndNotify(Payment payment) {
        return paymentPort.updatePayment(payment.id(),
                "[{\"op\": \"replace\",\"path\": \"/status\",\"value\": \"PAID\"}]")
            .flatMap(this::notify)
            .map(__ -> payment);
    }

    private Mono<ResponseEntity<Void>> notify(Payment payment) {
        return webClient.post()
            .uri(URI.create(payment.webhook()))
            .body(BodyInserters.fromValue(payment))
            .retrieve()
            .toBodilessEntity();
    }
}

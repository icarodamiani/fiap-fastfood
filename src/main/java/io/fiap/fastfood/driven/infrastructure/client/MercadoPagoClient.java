package io.fiap.fastfood.driven.infrastructure.client;

import io.fiap.fastfood.driven.infrastructure.core.entity.CustomerEntity;
import io.fiap.fastfood.driven.infrastructure.core.entity.OrderEntity;
import io.fiap.fastfood.driven.infrastructure.client.dto.MercadoPagoResponse;
import java.util.UUID;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MercadoPagoClient {

    public Mono<MercadoPagoResponse> createPayment(CustomerEntity customer, OrderEntity order) {
        return Mono.just(new MercadoPagoResponse(UUID.randomUUID().toString(), "ok"));
    }

    public Mono<MercadoPagoResponse> checkPayment(String paymentId) {
        return Mono.just(new MercadoPagoResponse(paymentId, "ok"));
    }
}

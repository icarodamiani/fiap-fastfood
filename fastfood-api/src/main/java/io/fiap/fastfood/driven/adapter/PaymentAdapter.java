package io.fiap.fastfood.driven.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.client.PaymentClient;
import io.fiap.fastfood.driven.core.domain.model.Payment;
import io.fiap.fastfood.driven.core.domain.payment.mapper.PaymentMapper;
import io.fiap.fastfood.driven.core.domain.payment.port.outbound.PaymentPort;
import io.fiap.fastfood.driven.core.domain.tracking.port.outbound.OrderTrackingPort;
import io.fiap.fastfood.driven.core.entity.PaymentEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.DuplicatedKeyException;
import io.fiap.fastfood.driven.repository.PaymentRepository;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaymentAdapter implements PaymentPort {

    private PaymentClient paymentClient;

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final ObjectMapper objectMapper;

    public PaymentAdapter(PaymentRepository repository, PaymentMapper mapper, ObjectMapper objectMapper,
                          OrderTrackingPort trackingPort,
                          PaymentClient paymentClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.paymentClient = paymentClient;
    }

    @Override
    public Mono<Payment> createPayment(Payment payment) {
        return repository.findByOrderId(payment.orderId())
            .next()
            .flatMap(c -> Mono.defer(() -> Mono.<PaymentEntity>error(DuplicatedKeyException::new)))
            .switchIfEmpty(Mono.defer(() -> repository.save(mapper.entityFromDomain(payment))
                .flatMap(entity -> {
                    return paymentClient.createPayment(mapper.domainFromEntity(entity))
                        .map(response -> entity);
                })))
            .map(mapper::domainFromEntity);
    }

    @Override
    public Mono<Payment> updatePayment(String id, String operations) {
        return repository.findById(id)
            .map(payment -> applyPatch().unchecked().apply(payment, operations))
            .flatMap(repository::save)
            .map(mapper::domainFromEntity)
            .onErrorMap(JsonPatchException.class::isInstance, BadRequestException::new);
    }

    private CheckedFunction2<PaymentEntity, String, PaymentEntity> applyPatch() {
        return (payment, operations) -> {
            var patch = readOperations()
                .unchecked()
                .apply(operations);

            var patched = patch.apply(objectMapper.convertValue(payment, JsonNode.class));

            return objectMapper.treeToValue(patched, PaymentEntity.class);
        };
    }

    private CheckedFunction1<String, JsonPatch> readOperations() {
        return operations -> {
            final InputStream in = new ByteArrayInputStream(operations.getBytes());
            return objectMapper.readValue(in, JsonPatch.class);
        };
    }
}

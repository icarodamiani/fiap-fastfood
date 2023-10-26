package io.fiap.fastfood.driver.controller.order.tracking;

import io.fiap.fastfood.driven.core.domain.order.tracking.mapper.OrderTrackingMapper;
import io.fiap.fastfood.driven.core.domain.order.tracking.port.inbound.OrderTrackingUseCase;
import io.fiap.fastfood.driven.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driver.controller.order.tracking.dto.OrderTrackingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/v1/order-tracking", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderTrackingController {

    private static final Logger LOGGER = getLogger(OrderTrackingController.class);

    private final OrderTrackingUseCase orderTrackingUseCase;
    private final OrderTrackingMapper orderTrackingMapper;
    private final HttpStatusExceptionConverter httpStatusExceptionConverter;

    public OrderTrackingController(OrderTrackingUseCase orderTrackingUseCase,
                                   OrderTrackingMapper orderTrackingMapper,
                                   HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.orderTrackingUseCase = orderTrackingUseCase;
        this.orderTrackingMapper = orderTrackingMapper;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PostMapping
    @Operation(description = "Create a order tracking")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplicated", content = @Content)
    })
    public Mono<ResponseEntity<OrderTrackingDTO>> create(@Validated @RequestBody OrderTrackingDTO body) {
        return orderTrackingUseCase.create(orderTrackingMapper.domainFromDto(body))
                .map(orderTrackingMapper::dtoFromDomain)
                .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(b))
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping("/order")
    @Operation(description = "Find order by id")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<OrderTrackingDTO> find(@RequestParam String orderId) {
        return orderTrackingUseCase.find(orderId)
                .map(orderTrackingMapper::dtoFromDomain)
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping("/orders/all")
    @Operation(description = "Find all orders with status not FINISHED")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Flux<OrderTrackingDTO> findAll(Pageable pageable) {
        AtomicInteger start = new AtomicInteger();
        AtomicInteger end = new AtomicInteger();
        return orderTrackingUseCase.findAll(pageable)
                .map(orderTrackingMapper::dtoFromDomain)
                .collectList()
                .doOnNext(orderTrackingDTOS -> {
                    start.set(Math.min((int) pageable.getOffset(), orderTrackingDTOS.size()));
                    end.set(Math.min((start.get() + pageable.getPageSize()), orderTrackingDTOS.size()));
                })
                .map(orderTrackingDTOS -> (Page<OrderTrackingDTO>) new PageImpl<>(orderTrackingDTOS
                        .subList(start.get(), end.get()), pageable, orderTrackingDTOS.size()))
                .map(Slice::getContent)
                .flatMapMany(Flux::fromIterable)
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }
}

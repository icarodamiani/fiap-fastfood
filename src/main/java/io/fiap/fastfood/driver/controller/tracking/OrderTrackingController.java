package io.fiap.fastfood.driver.controller.tracking;

import io.fiap.fastfood.driven.core.domain.tracking.mapper.OrderTrackingMapper;
import io.fiap.fastfood.driven.core.domain.tracking.port.inbound.OrderTrackingUseCase;
import io.fiap.fastfood.driven.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driver.controller.tracking.dto.OrderTrackingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/v1/tracking", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/{orderId}")
    @Operation(description = "Find order by id")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<OrderTrackingDTO> findByOrderId(@PathVariable String orderId) {
        return orderTrackingUseCase.findByOrderId(orderId)
                .map(orderTrackingMapper::dtoFromDomain)
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping("/report")
    @Operation(description = "Tracking report")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Flux<OrderTrackingDTO> find(Pageable pageable) {
        return orderTrackingUseCase.find(pageable)
            .map(orderTrackingMapper::dtoFromDomain)
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

}

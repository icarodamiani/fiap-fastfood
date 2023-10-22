package io.fiap.fastfood.driver.controller;

import io.fiap.fastfood.driven.core.domain.salespoint.mapper.SalesPointMapper;
import io.fiap.fastfood.driven.core.domain.salespoint.port.inbound.SalesPointUseCase;
import io.fiap.fastfood.driven.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driver.controller.dto.SalesPointDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/v1/salespoint", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesPointController {
    private static final Logger LOGGER = getLogger(io.fiap.fastfood.driver.controller.SalesPointController.class);
    private final SalesPointMapper mapper;
    private final SalesPointUseCase salesPointUseCase;
    private final HttpStatusExceptionConverter httpStatusExceptionConverter;

    public SalesPointController(SalesPointMapper mapper,
                                SalesPointUseCase salesPointUseCase,
                                HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.mapper = mapper;
        this.salesPointUseCase = salesPointUseCase;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PostMapping
    @Operation(description = "Create a salespoint")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplicated", content = @Content)
    })
    public Mono<ResponseEntity<SalesPointDTO>> create(@Validated @RequestBody SalesPointDTO body) {
        return salesPointUseCase.create(mapper.domainFromDto(body))
                .map(mapper::dtoFromDomain)
                .map(b -> ResponseEntity.status(HttpStatus.CREATED).body(b))
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Operation(description = "Update a salespoint")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<SalesPointDTO>> update(@PathVariable String id,
                                                      @RequestBody SalesPointDTO body) {
        return salesPointUseCase.update(id, body)
                .map(mapper::dtoFromDomain)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a salespoint")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted."),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return salesPointUseCase.delete(id)
                .map(__ -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .defaultIfEmpty(ResponseEntity.noContent().build())
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping
    @Operation(description = "Find salespoint")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<SalesPointDTO> find(@RequestParam(required = false) String id) {
        return salesPointUseCase.find(id)
                .map(mapper::dtoFromDomain)
                .onErrorMap(e ->
                        new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
                .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }
}

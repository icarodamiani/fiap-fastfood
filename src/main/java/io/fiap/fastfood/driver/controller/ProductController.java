package io.fiap.fastfood.driver.controller;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.core.domain.product.port.inbound.CreateProductUseCase;
import io.fiap.fastfood.core.domain.product.port.inbound.DeleteProductUseCase;
import io.fiap.fastfood.core.domain.product.port.inbound.ListProductUseCase;
import io.fiap.fastfood.core.domain.product.port.inbound.UpdateProductUseCase;
import io.fiap.fastfood.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driver.controller.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/v1/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {

    private static final Logger LOGGER = getLogger(ProductController.class);

    private ProductMapper mapper;
    private CreateProductUseCase createProductUseCase;
    private UpdateProductUseCase updateProductUseCase;
    private DeleteProductUseCase deleteProductUseCase;
    private ListProductUseCase listProductUseCase;

    private HttpStatusExceptionConverter httpStatusExceptionConverter;

    public ProductController(ProductMapper mapper,
                             CreateProductUseCase createProductUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             ListProductUseCase listProductUseCase,
                             HttpStatusExceptionConverter httpStatusExceptionConverter) {
        this.mapper = mapper;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.listProductUseCase = listProductUseCase;
        this.httpStatusExceptionConverter = httpStatusExceptionConverter;
    }

    @PostMapping
    @Operation(description = "Create a product")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Added"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "409", description = "Duplicated", content = @Content)
    })
    public Mono<ResponseEntity<ProductDTO>> create(@Validated @RequestBody ProductDTO value) {
        return createProductUseCase.create(mapper.domainFromDto(value))
            .map(mapper::dtoFromDomain)
            .map(v -> ResponseEntity.status(HttpStatus.CREATED).body(v))
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    @Operation(description = "Update a product")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<ProductDTO>> update(@PathVariable String id,
                                                   @RequestBody JsonPatch operations) {
        return updateProductUseCase.update(id, operations)
            .map(mapper::dtoFromDomain)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted."),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return deleteProductUseCase.delete(id)
            .map(__ -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
            .defaultIfEmpty(ResponseEntity.noContent().build())
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }

    @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
    @Operation(description = "List products")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    public Flux<ProductDTO> find(@RequestParam(required = false) String typeId, Pageable pageable) {
        return listProductUseCase.list(typeId, pageable)
            .map(mapper::dtoFromDomain)
            .onErrorMap(e ->
                new ResponseStatusException(httpStatusExceptionConverter.convert(e), e.getMessage(), e))
            .doOnError(throwable -> LOGGER.error(throwable.getMessage(), throwable));
    }
}

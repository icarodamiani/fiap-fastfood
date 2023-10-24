package io.fiap.fastfood.driven.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.configuration.WebConfig;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driven.core.domain.model.Product;
import io.fiap.fastfood.driven.core.domain.model.ProductType;
import io.fiap.fastfood.driven.core.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.driven.core.domain.product.port.inbound.ProductUseCase;
import io.fiap.fastfood.driver.controller.product.ProductController;
import io.fiap.fastfood.driver.controller.product.dto.ProductDTO;
import io.fiap.fastfood.driver.controller.product.dto.ProductTypeDTO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ProductController.class)
@Import({
    WebConfig.class,
})
@ActiveProfiles("test")
class ProductControllerTest {

    @MockBean
    private ProductUseCase productUseCase;

    @MockBean
    private ProductMapper mapper;

    @MockBean
    private HttpStatusExceptionConverter converter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApplicationContext context;

    private WebTestClient webTestClient;


    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void create_success() {
        var dtoType = ProductTypeDTO.ProductTypeDTOBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var dto = ProductDTO.ProductDTOBuilder.builder()
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(dtoType).build();

        var domainType = ProductType.ProductTypeBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var domain = Product.ProductBuilder.builder()
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(domainType).build();


        when(mapper.domainFromDto(dto)).thenReturn(domain);
        when(productUseCase.create(domain)).thenReturn(Mono.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.post()
            .uri("/v1/products")
            .body(Mono.just(dto), ProductDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ProductDTO.class)
            .isEqualTo(dto);
    }

    @Test
    void update_success() throws IOException {
        String json = "[{ \"op\":\"replace\", \"path\":\"/price\", \"value\":\"10\" }]";
        var patch = jsonPatch(json);

        var domainType = ProductType.ProductTypeBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var domain = Product.ProductBuilder.builder()
            .withId(11L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(domainType).build();

        var dtoType = ProductTypeDTO.ProductTypeDTOBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var dto = ProductDTO.ProductDTOBuilder.builder()
            .withId(11L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(dtoType).build();

        when(productUseCase.update(anyLong(), any())).thenReturn(Mono.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.patch()
            .uri("/v1/products/" + 11L)
            .body(Mono.just(patch), JsonPatch.class)
            .header("Content-Type", "application/json-patch+json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(ProductDTO.class)
            .isEqualTo(dto);
    }

    @Test
    void update_not_found() throws IOException {
        String json = "[{ \"op\":\"replace\", \"path\":\"/price\", \"value\":\"10\" }]";
        var patch = jsonPatch(json);

        when(productUseCase.update(anyLong(), any())).thenReturn(Mono.empty());

        webTestClient.patch()
            .uri("/v1/products/" + 11L)
            .body(Mono.just(patch), JsonPatch.class)
            .header("Content-Type", "application/json-patch+json")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    void update_invalid_patch() throws IOException {
        String json = "[{ \"op\":\"replace\", \"path\":\"/unknown\", \"value\":\"any value\" }]";
        var patch = jsonPatch(json);
        var exception = new BadRequestException();

        when(productUseCase.update(anyLong(), any())).thenReturn(Mono.error(exception));
        when(converter.convert(exception)).thenReturn(400);

        webTestClient.patch()
            .uri("/v1/products/" + 11L)
            .body(Mono.just(patch), JsonPatch.class)
            .header("Content-Type", "application/json-patch+json")
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void delete_success() {
        when(productUseCase.delete(11L)).thenReturn(Mono.just(11L).then());

        webTestClient.delete()
            .uri("/v1/products/" + 11L)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void list_success() {
        var domainType = ProductType.ProductTypeBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var domain = Product.ProductBuilder.builder()
            .withId(11L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(domainType).build();

        var dtoType = ProductTypeDTO.ProductTypeDTOBuilder.builder()
            .withDescription("any")
            .withId(5L).build();

        var dto = ProductDTO.ProductDTOBuilder.builder()
            .withId(11L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L)
            .withType(dtoType).build();

        when(productUseCase.list(any(), any())).thenReturn(Flux.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.get()
            .uri(uriBuilder ->
                uriBuilder
                    .path("/v1/products")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build())
            .exchange()
            .expectStatus().isOk()
            .returnResult(ProductDTO.class)
            .getResponseBody()
            .subscribe();
    }

    private JsonPatch jsonPatch(String json) throws IOException {
        final InputStream in = new ByteArrayInputStream(json.getBytes());

        return objectMapper.readValue(in, JsonPatch.class);
    }
}

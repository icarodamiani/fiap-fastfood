package io.fiap.fastfood.driver.controller.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.fiap.fastfood.driven.core.configuration.WebConfig;
import io.fiap.fastfood.driven.core.domain.customer.mapper.CustomerMapper;
import io.fiap.fastfood.driven.core.domain.customer.port.inbound.CustomerUseCase;
import io.fiap.fastfood.driven.core.domain.model.Customer;
import io.fiap.fastfood.driven.core.domain.model.Identity;
import io.fiap.fastfood.driven.core.domain.model.Product;
import io.fiap.fastfood.driven.core.domain.model.ProductType;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.HttpStatusExceptionConverter;
import io.fiap.fastfood.driver.controller.customer.dto.CustomerDTO;
import io.fiap.fastfood.driver.controller.customer.dto.IdentityDTO;
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

@WebFluxTest(CustomerController.class)
@Import({
    WebConfig.class,
})
@ActiveProfiles("test")
class CustomerControllerTest {

    @MockBean
    private CustomerUseCase customerUseCase;

    @MockBean
    private CustomerMapper mapper;

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
        var dtoIdentity = IdentityDTO.IdentityDTOBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var dto = CustomerDTO.CustomerDTOBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(dtoIdentity).build();

        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var domain = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        when(mapper.domainFromDto(dto)).thenReturn(domain);
        when(customerUseCase.create(domain)).thenReturn(Mono.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.post()
            .uri("/v1/customers")
            .body(Mono.just(dto), CustomerDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(CustomerDTO.class)
            .isEqualTo(dto);
    }

    @Test
    void update_success() throws IOException {
        String json = "[{ \"op\":\"replace\", \"path\":\"/email\", \"value\":\"doe.john@gmail.com\" }]";
        var patch = jsonPatch(json);

        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var domain = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        var dtoIdentity = IdentityDTO.IdentityDTOBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var dto = CustomerDTO.CustomerDTOBuilder.builder()
            .withName("John Doe")
            .withEmail("doe.john@gmail.com")
            .withIdentity(dtoIdentity).build();

        when(customerUseCase.update(anyLong(), any())).thenReturn(Mono.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.patch()
            .uri("/v1/customers/" + 11L)
            .body(Mono.just(patch), JsonPatch.class)
            .header("Content-Type", "application/json-patch+json")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDTO.class)
            .isEqualTo(dto);
    }

    @Test
    void update_not_found() throws IOException {
        String json = "[{ \"op\":\"replace\", \"path\":\"/email\", \"value\":\"doe.john@gmail.com\" }]";
        var patch = jsonPatch(json);

        when(customerUseCase.update(anyLong(), any())).thenReturn(Mono.empty());

        webTestClient.patch()
            .uri("/v1/customers/" + 11L)
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

        when(customerUseCase.update(anyLong(), any())).thenReturn(Mono.error(exception));
        when(converter.convert(exception)).thenReturn(400);

        webTestClient.patch()
            .uri("/v1/customers/" + 11L)
            .body(Mono.just(patch), JsonPatch.class)
            .header("Content-Type", "application/json-patch+json")
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void delete_success() {
        when(customerUseCase.delete(11L)).thenReturn(Mono.just(11L).then());

        webTestClient.delete()
            .uri("/v1/customers/" + 11L)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    void list_success() {
        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var domain = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        var dtoIdentity = IdentityDTO.IdentityDTOBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var dto = CustomerDTO.CustomerDTOBuilder.builder()
            .withName("John Doe")
            .withEmail("doe.john@gmail.com")
            .withIdentity(dtoIdentity).build();

        when(customerUseCase.list(any())).thenReturn(Flux.just(domain));
        when(mapper.dtoFromDomain(domain)).thenReturn(dto);

        webTestClient.get()
            .uri(uriBuilder ->
                uriBuilder
                    .path("/v1/customers")
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .build())
            .exchange()
            .expectStatus().isOk()
            .returnResult(CustomerDTO.class)
            .getResponseBody()
            .subscribe();
    }

    private JsonPatch jsonPatch(String json) throws IOException {
        final InputStream in = new ByteArrayInputStream(json.getBytes());

        return objectMapper.readValue(in, JsonPatch.class);
    }
}
